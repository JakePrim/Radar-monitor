## DataItem
1. 直接的使用方式:
* DataBindingMultipleAdapter 基础上升级的多类型Adapter管理，目前Item已经具备了 MVVM的开发模式，
* 同时也可以获取ContextViewModel 和 ApplicationViewModel实现了组件之间的通信
* Item 具备了自己的生命周期，类似于一个Page，在多类型中Item 解耦的更加彻底，但是在DataBindingMultipleAdapter中
* 需要手动创建Item的实例并且要循环遍历data数据，将data传递给Item实例，此过程过于繁琐
*
2. 注入Item，手动管理Type:
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
3. 自动注入Item：
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
