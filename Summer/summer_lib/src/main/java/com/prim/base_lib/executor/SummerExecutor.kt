package com.prim.base_lib.executor

import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import com.prim.base_lib.log.SummerLog
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * @desc 多线程操作框架封装,支持任务的优先级去执行，支持线程池的暂停、恢复
 * todo 线程池能力监控、耗时任务检测、定时
 * @author sufulu
 * @time 4/29/21 - 4:12 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
object SummerExecutor {
    private var executor: ThreadPoolExecutor
    private var lock: ReentrantLock
    private var pausedCondition: Condition
    private var mainHandler = Handler(Looper.getMainLooper())

    //标记是否暂停线程
    private var isPaused: Boolean = false

    //执行线程的数量
    private var threadSize = 0

    init {
        lock = ReentrantLock()
        pausedCondition = lock.newCondition()
        //CPU的数量
        val cpuCount = Runtime.getRuntime().availableProcessors()
        //核心线程数
        val corePoolSize = cpuCount + 1
        //最大线程数
        val maxPoolSize = cpuCount * 2 + 1

        //out 表示Runnable或者Runnable的子类
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()

        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS
        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread()
            thread.name = "summer-executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }
        //构建线程池
        executor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                //在执行线程之前进行判断 是否暂停线程如果暂停线程 使用ReentrantLock进行阻塞
                if (isPaused) {
                    lock.lock()
                    try {
                        //进行阻塞
                        pausedCondition.await()
                        threadSize++
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                threadSize--
                //监控线程池的耗时任务、线程创建数量、正在运行的数量
                SummerLog.e("已执行完的任务的优先级是:" + (r as PriorityRunnable).priority)
            }
        }
    }

    /**
     * 返回执行线程的数量
     */
    fun executeThreadSize():Int{
        return threadSize
    }

    /**
     * 执行线程
     */
    @JvmOverloads
    fun executor(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        executor.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun executor(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Callable<*>) {
        executor.execute(PriorityRunnable(priority, runnable))
    }

    /**
     * 回调了
     */
    abstract class Callable<T> : Runnable {
        override fun run() {
            //执行子线程任务之前
            mainHandler.post { onPrepare() }
            //执行子线程的任务
            val t = onBackground()
            //任务执行完毕 返回结果到主线程中
            mainHandler.post { onCompleted(t) }
        }

        /**
         * 异步任务之前
         */
        open fun onPrepare() {

        }

        /**
         * 子线程中执行的任务
         */
        abstract fun onBackground(): T

        /**
         * 执行完毕后 将结果返回到主线程中
         */
        abstract fun onCompleted(t: T)
    }

    /**
     * 任务优先级的Runnable类
     */

    class PriorityRunnable(val priority: Int = 0, val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (priority > other.priority) 1 else if (priority < other.priority) -1 else 0
        }

    }

    /**
     * 暂停线程
     */
    @Synchronized
    fun pause() {
        isPaused = true
        SummerLog.i("SummerExecutor is paused")
    }

    /**
     * 恢复线程
     */
    @Synchronized
    fun resume() {
        isPaused = false
        lock.lock()
        try {
            //唤醒所有阻塞的线程
            pausedCondition.signalAll()
        } finally {
            lock.unlock()
        }
        SummerLog.i("SummerExecutor is resume")
    }
}