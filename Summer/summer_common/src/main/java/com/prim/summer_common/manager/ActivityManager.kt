package com.prim.summer_common.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

/**
 * @desc Activity管理栈
 * @author sufulu
 * @time 4/10/21 - 12:43 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
class ActivityManager private constructor() {
    //存储Activity的引用
    private val activityRefs = ArrayList<WeakReference<Activity>>()

    //Activity进入前台和后台的回调
    private val frontbackCallback = ArrayList<FrontBackCallback>()

    /**
     * 记录Activity的数量
     */
    private var activityStartCount = 0

    /**
     * 记录Activity是在前台还是后台
     */
    private var front = true

    /**
     * 初始化
     */
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    /**
     * 监听Activity的生命周期
     * 内部类
     */
    inner class InnerActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity?) {
            activityStartCount++
            //activityStartCount > 0 说明应用处于可见状态
            //判断之前是不是在后台
            if (!front && activityStartCount > 0) {
                front = true
                //从后台切换到前台
                onFrontBackChange(front)
            }
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityStopped(activity: Activity?) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                front = false
                onFrontBackChange(front)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            for (ref in activityRefs) {
                if (ref.get() == activity) {
                    activityRefs.remove(ref)
                    break
                }
            }
        }
    }

    /**
     * 判断APP是否进入后台
     */
    private fun onFrontBackChange(front: Boolean) {
        for (callback in frontbackCallback) {
            callback.onChange(front)
        }
    }

    /**
     * 获取栈顶的Activity
     */
    val topActivity: Activity?
        get() {
            if (activityRefs.size <= 0) {
                return null
            } else {
                return activityRefs[activityRefs.size - 1].get()
            }
        }


    /**
     * 添加回调
     */
    fun addFrontBackCallback(callback: FrontBackCallback) {
        frontbackCallback.add(callback)
    }

    /**
     * 移除回调
     */
    fun removeFrontBackCallback(callback: FrontBackCallback) {
        if (frontbackCallback.contains(callback)) {
            frontbackCallback.remove(callback)
        }
    }

    interface FrontBackCallback {
        fun onChange(front: Boolean)
    }

    /**
     * 实现单例模式
     */
    companion object {
        //lazy 延迟属性
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }
}