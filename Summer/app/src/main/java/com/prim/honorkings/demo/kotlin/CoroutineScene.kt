package com.prim.honorkings.demo.kotlin

import android.content.res.AssetManager
import android.provider.Settings
import com.prim.base_lib.executor.SummerExecutor
import com.prim.base_lib.log.SummerLog
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import kotlin.Result

/**
 * @desc
 * @author sufulu
 * @time 5/5/21 - 7:02 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
object CoroutineScene {

    /**
     * 启动一个线程，串行执行
     */
    fun startScene1() {
        //协程
        GlobalScope.launch(Dispatchers.IO) {
            SummerLog.e("coroutine is running")
            val r1 = request1()
            val r2 = request2(r1)
            val r3 = request3(r2)
            updateUI(r3)
        }
        SummerLog.e("coroutine has launched")

    }

    /**
     * 启动一个线程，先执行request1，执行完毕之后同时运行request2和request3
     */
    fun startScene2() {
        GlobalScope.launch(Dispatchers.Main) {
            SummerLog.e("coroutine is running")
            val result1 = request1()
            //执行完request1 后并发执行request2和request3
            val deferred = GlobalScope.async {
                request2(result1)
            }
            val deferred2 = GlobalScope.async {
                request3(result1)
            }
            //必须同时调用调用await方法
            updateUI(deferred.await(), deferred2.await())
        }
    }

    private fun updateUI(r2: String, r3: String) {
        SummerLog.e("updateUI r2=${r2} r3=${r3}")
    }

    private fun updateUI(r3: String) {
        SummerLog.e("updateUI  ${r3}")
    }

    //suspend 关键字的作用：挂起函数
    private suspend fun request1(): String {
        delay(2000)// delay 不会暂停线程，但会暂停当前所在的协程
        SummerLog.e("request1 -> this is ${Thread.currentThread().name}")
        return "request1"
    }

    private suspend fun request2(result: String): String {
        delay(2000)
        SummerLog.e("request2 -> result:${result} this is ${Thread.currentThread().name}")
        return "request2"
    }

    private suspend fun request3(result: String): String {
        delay(2000)
        SummerLog.e("request3 -> result:${result} this is ${Thread.currentThread().name}")
        return "request3"
    }


    /**
     * 演示以异步的方式，读取assets目录下的文件，适配协程的写法
     */
    suspend fun parseAssetsFile(assetManager: AssetManager, fileName: String): String {
        return suspendCancellableCoroutine { continuation ->
            Thread(Runnable {
                val inputStream = assetManager.open(fileName)
                val br = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                var stringBuilder = StringBuilder()
                do {
                    line = br.readLine()
                    if (line != null) stringBuilder.append(line) else break
                } while (true)
                inputStream.close()
                br.close()
                Thread.sleep(2000)
                //恢复协程
                continuation.resumeWith(Result.success(stringBuilder.toString()))
            }).start()
        }
    }
}