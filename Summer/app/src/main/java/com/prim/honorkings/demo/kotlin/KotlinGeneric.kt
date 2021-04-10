package com.prim.honorkings.demo.kotlin

import java.util.Comparator

/**
 * @desc
 * @author sufulu
 * @time 4/6/21 - 7:21 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
fun main() {
//    val coke = Coke()
//    coke.taste()
//    coke.price(Sweet())

    BlueColor(Blue()).printColor()
    test2()
}

/**-----------------泛型接口-----------------------**/
interface Drinks<T> {
    fun taste(): T
    fun price(t: T)
}

class Sweet {
    val price = 5
}

class Coke : Drinks<Sweet> {
    override fun taste(): Sweet {
        println("sweet")
        return Sweet()
    }

    override fun price(t: Sweet) {
        println("coke price:${t.price}")
    }
}

/**----------------------泛型类------------------------**/
abstract class Color<T>(var t: T/*泛型字段*/) {
    abstract fun printColor()
}

class Blue {
    val color = "Blue"
}

class BlueColor(t: Blue) : Color<Blue>(t) {
    override fun printColor() {
        println("color is ${t.color}")
    }
}

/**------------------------泛型方法----------------------**/
fun <T> formJson(json: String, tClass: Class<T>): T? {
    var t: T = tClass.newInstance()
    return t
}

/**---------------泛型约束-------------------------**/
//泛型约束T为Comparable的子类型
fun <T : Comparable<T>?> sort(list: List<T>?): Unit {}
fun test2() {
    sort(listOf<Int>(1, 2, 3))//public class Int private constructor() : Number(), Comparable<Int>

//    sort(listOf(Blue())) //  inferred type Blue is not a subtype of Comparable<Blue>?

    val listString =
        listOf("a", "b", "c")//String public class String : Comparable<String>, CharSequence
    val list = test(listString, "b")
    println(list)//c
}

//泛型多个边界的情况
fun <T> test(list: List<T>, threshold: T): List<T>
        where T : CharSequence, T : Comparable<T> {
    return list.filter { it > threshold }.map { it }
}

/**---------------------泛型协变和逆变------------------------**/
//kotlin的协变 out  <out Number> 对应着java：<? extend Number>
//kotlin的逆变 in   <in Int>  对应java <? super Integer>