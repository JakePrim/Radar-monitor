package com.prim.honorkings.demo.kotlin

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

/**
 * @desc kotlin扩展学习
 * @author sufulu
 * @time 4/6/21 - 9:55 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */

fun main() {
    val li = mutableListOf(1, 2, 3, 4, 5)
    li.swap(2, 3)

    println(li)//1 2 4 3 5

    println("abcde1".lastChar)//1

    //伴生对象的扩展
    Jump.print("123")

    testLet(null)

    testApply()
}

/**----------------扩展方法-----------------------**/
//扩展mutableList 完两个元素的互换
//在java中仅仅是静态方法 并不会带来性能的提升 而是带来了代码的易用性,扩展性
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

/**---------------------扩展属性-----------------**/
//为String添加一个lastChar属性
val String.lastChar: Char
    get() = this.get(this.length - 1)

/**------------------扩展伴生对象-----------------**/
class Jump {
    //伴生对象 静态方法和静态属性
    companion object {

    }
}

fun Jump.Companion.print(str: String) {
    println(str)
}

/**-------------------扩展函数-----------------**/
//let 扩展函数实际上是一个作用域函数，还可以避免写一些null操作
fun testLet(str: String?) {
    str.let {
        var str2 = "扩展"
        println(it + str2)//null扩展
    }
    //避免为空的操作
    str?.let {
        println(it.length)
    }
}

//run 扩展函数：可以直接访问实例的公有属性和方法
data class Room(var address: String, var price: Float, var size: Int)

fun testRun(room: Room) {
    //一般要访问属性和方法需要 xx.xxx
    room.address = "123"

    room.run {
        //run的扩展函数可以直接访问
        println("Room:${address},${price},${size}")
    }
}

//apply 扩展函数: 调用某对象的apply的扩展函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象
fun testApply() {
    ArrayList<String>().apply {
        add("1")
        add("2")
        add("3")
    }.let {
        println(it)
    }
}

/***------------扩展空间绑定以及监听---------------**/
//给Activity扩展控件绑定
fun <T : View> Activity.find(@IdRes id: Int): T {
    return this.findViewById(id)
}

//扩展事件监听 可以对Int或者View进行扩展
fun View.onClick(click: () -> Unit) {
    //this->View
    this.apply {
        setOnClickListener {
            click()
        }
    }
}

//对资源id进行扩展
fun <T : View> Int.onClick(activity: Activity, click: (View) -> Unit) {
    //this->id
    activity.find<T>(this).apply {
        setOnClickListener {
            click(it)
        }
    }
}