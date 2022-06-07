package com.fbe.lib_dataitem

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation


/**
 * @author sufulu
 * 1.0V:
 * DataBindingMultipleAdapter 基础上升级的多类型Adapter管理，目前Item已经具备了 MVVM的开发模式，
 * 同时也可以获取ContextViewModel 和 ApplicationViewModel实现了组件之间的通信
 * Item 具备了自己的生命周期，类似于一个Page，在多类型中Item 解耦的更加彻底，但是在DataBindingMultipleAdapter中
 * 需要手动创建Item的实例并且要循环遍历data数据，将data传递给Item实例，此过程过于繁琐
 *
 * plus:2.0V
 * @Item 注入Item
 * MultipleAdapter：register() 注入DataItem,识别@Item缓存映射,通过注入的Data自动，注入识别Item 并创建实例
 * 通过对数据data的插入、删除、更新 自动更新目标的Item，让开发人员更加专注于业务逻辑处理.
 *
 * 但是通过反射创建实例 会带来性能上的开销，在 *代码可维护性* 和 *牺牲点性能* 之间的决策
 * Kotlin的反射性能比Java的反射高出近一倍，https://coolrc.me/2021/07/29/202107291739/，牺牲点性能上的消耗提高代码的可维护性
 *
 * MultipleAdapter通过register注入Item
 * 通过addData数据，就可以实现Item类型注入，代码更容易维护，Adapter和Item进行分离
 *
 * plusplus:3.0V
 * @ItemAuto 注解会通过APT自动生成的缓存Type的class,在Application中调用注入autoInjectInit即可，
 * 省去了register()当大量类型，需要很很多DataItem::class，通过APT自动扫描缓存映射会非常方便
 * 自动生成的class如下：
 * public final class ItemType {
 *    public static void cacheType(HashMap<String, Class> map) {
 *      System.out.println("Hello, ItemType auto process @Item annotation!");
 *      map.put("search_action",SearchActionDataItem.class);
 *      ......
 *  }
 * }
 *
 * 2.0V 和 3.0V都可以稳定使用：
 * 2.0V @Item 需要结合register() 方法注入DataItem
 * 3.0V @ItemAuto 自动注入DataItem
 */
open class MultipleAdapter(
    context: Context,
    lifecycleOwner: LifecycleOwner? = null
) : DataBindingMultipleAdapter(context, lifecycleOwner) {
    /**
     * 缓存类型的params构造参数
     */
    val cacheItemParams = HashMap<String, Array<out Any?>>()

    companion object {
        /**
         * 缓存类型和DataItem的KClass
         */
        private val cacheItemClazz = HashMap<String, KClass<*>>()

        /**
         * Java中的Class
         */
        private val tempItemClazz = HashMap<String, Class<*>>()


        /**
         * 自动注入类型和class
         * @param moduleName 一定要和模块名对应否则无法找到类
         */
        fun autoInjectInit(context: Context, moduleName: String) {
            Class.forName("com.dayunauto.item.apt.$moduleName.ItemType").getDeclaredMethod(
                "cacheType",
                HashMap::class.java,
            ).invoke(null, tempItemClazz)
            Log.e("MultipleAdapter", "autoInjectInit: check --->")
            tempItemClazz.forEach {
                cacheItemClazz[it.key] = it.value.kotlin
                Log.e(
                    "MultipleAdapter",
                    "autoInjectInit: key:${it.key} - value: ${it.value.simpleName}; total:${tempItemClazz.size}"
                )
            }
            Log.e("MultipleAdapter", "autoInjectInit: checked <---")
        }
    }

    /**
     * 注册Item
     */
    fun <D : DataItem<*, *, *>> register(clazz: KClass<D>): RegisterBuilder {
        //通过class 拿到注解值，根据type类型存储DataItem::class
        val item = clazz.findAnnotation<Item>()
        var type = ""
        item?.let {
            type = it.type//获取type类型
            //缓存KClass 直接从缓存中拿取创建实例
            if (!cacheItemClazz.containsKey(type)) {
                cacheItemClazz[type] = clazz
            }
        }
        return RegisterBuilder(type)
    }

    /**
     * 当对某个类型设置数据时，需要改变构造函数的参数值，调用该方法
     */
    fun to(type: String, vararg params: Any?): MultipleAdapter {
        return RegisterBuilder(type).to(*params)
    }

    /**
     * 只需要添加数据和对应的type字符串即可 轻松管理任何item
     * 添加数据,一条数据对应一个或多个Type,显示item的顺序和type的顺序一致
     * reified 会自动推导T的Type
     */
    inline fun <reified T : Any> addData(data: T, vararg type: String) {
        type.forEach {
            addItem(findItemObj(it, data))
        }
    }

    /**
     * 将某个item插入到某个位置
     */
    inline fun <reified T : Any> addDataAt(position: Int, data: T, type: String) {
        addItemAt(position, findItemObj(type, data))
    }

    /**
     * 一堆list数据，对应一个type
     */
    inline fun <reified T : Any> addDataList(data: List<T>, type: String) {
        val list = arrayListOf<DataItem<*, *, *>>()
        data.forEach {
            list.add(findItemObj(type, it))
        }
        addItems(list)
    }

    /**
     * 快速校验Type是否在仓库中存在
     */
    fun fastVerifyType(type: String) {
        val result = cacheItemClazz.containsKey(type)
        if (!result) {
            throw RuntimeException("data : MultipleType is Type:${type} & CacheItem Type inconformity.")
        }
    }

    /**
     * 快速校验List数据是否正确
     * list数据，对应一个或多个type,该方法实现快速校验 List数据中的Type是否和仓库中的Type一致，如果不一致 抛出异常
     */
    fun <T : MultipleType> addDataTypeListFastVerify(
        data: List<T>
    ) {
        data.forEach { bean ->
            if (bean.getType().isNotEmpty()) {
                //校验type
                fastVerifyType(bean.getType())
            }
        }
    }

    inline fun <reified T : MultipleType> addDataTypeList(data: List<T>) {
        val list = arrayListOf<DataItem<*, *, *>>()
        data.forEach { bean ->
            if (bean.getType().isNotEmpty()) {
                //匹配type
                list.add(findItemObj(bean.getType(), bean))
            }
        }
        addItems(list)
    }

    /**
     * 移除某个位置的item,判断类型是否相同
     */
    fun removeDataAt(position: Int, type: String) {
        if (getOriginalItemSize() > position) {
            val item = getData()[position] //获取当前位置的item实例
            val clazz = cacheItemClazz[type]//获取type下的class
            Log.e("TAG", "removeDataAt:${item.javaClass} : ${clazz?.java}  ")
            //判断class是否相同
            if (item.javaClass == clazz?.java) {
                removeItem(item)
            }
        }
    }

    /**
     * 直接移除某个position
     */
    fun removePosition(position: Int) {
        if (getOriginalItemSize() > position) {
            val item = getData()[position]
            removeItem(item)
        }
    }

    /**
     * 根据type去返回一个Item实例对象
     */
    fun findItemObj(type: String, bean: Any): DataItem<*, *, *> {
        //通过type找到对应的Item
        val itemParams = cacheItemParams[type]
        val itemClazz = cacheItemClazz[type]
        itemClazz?.let { clazz ->
            var kClazz = clazz
            if (clazz is Class<*>) {
                Log.e("TAG", "findItemObj: clazz is Class to KClass")
                kClazz = clazz.kotlin
            }
            //获取构造方法
            val cons = kClazz.constructors
            cons.forEach { function ->
                return if (itemParams != null) {
                    //调用构造方法，生成实例对象
                    val itemObj = function.call(bean, *itemParams)
                    itemObj as DataItem<*, *, *>
                } else {
                    val itemObj = function.call(bean)
                    itemObj as DataItem<*, *, *>
                }
            }
        }
        throw RuntimeException("没有找到type:$type,下对应的DataItem,请确认@Item(type)中是否有相应的DataItem")
    }

    inner class RegisterBuilder(val type: String) {
        /**
         * 记录该item类型需要传递的构造参数,除了data参数
         */
        fun to(vararg params: Any?): MultipleAdapter {
            if (type.isEmpty()) {
                throw RuntimeException("type not empty or null")
            }
            cacheItemParams[type] = params
            return this@MultipleAdapter
        }
    }
}

