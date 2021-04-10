package com.prim.honorkings.demo.kotlin

/**
 * @desc
 * @author sufulu
 * @time 4/6/21 - 3:47 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */

fun main() {
    println(functionLearn(101))//true
    //类的成员方法
    Person().text1()
    //静态方法
    Person.test2()
    //静态类 类似java的utils工具类
    println(NumUtils.double(2))//4
    println(double(3))

    println(append('a', 'b', 'c'))//abc
    println(magic())

    test1()
    println(test3(2, 3))//5
    test5()

    val lists = listOf<Int>(1, 2, 3)
    //调用list的扩展函数sum
    val sum = lists.sum { println("it:${it}") }
    println(sum)


    val liststr = listOf<String>("1", "2", "3")
    val result = liststr.toIntSum()(2)
    println(result)

    testCourse(1)(2) {
        println(it)//3
    }

    test6() //message:success,code:0

    test8()
}

fun functionLearn(day: Int): Boolean {
    return day > 100
}

//类的成员方法
class Person {
    /**
     * 成员方法
     */
    fun text1() {
        println("成员方法")
    }

    /**
     * 静态方法/类方法
     */
    companion object {
        fun test2() {
            println("companion object实现类方法：test2()")
        }
    }
}

/**
 * object 定义静态类，里面定义的方法都是静态方法
 */
object NumUtils {
    fun double(d: Int): Int {
        return d * d
    }
}

/**
 * 单表达式方法
 * */
fun double(d: Int): Int = d * d

/**
 * 默认值，方法参数可以有默认值，当省略相应的参数时使用默认值。与其java相比可以减少重载数量
 */
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {

}

/**
 * 可变数量的参数
 */
fun append(vararg str: Char): String {
    val result = StringBuffer()
    for (c in str) {
        result.append(c)
    }
    return result.toString()
}

/**
 * 局部方法
 */
fun magic(): Int {
    fun foo(v: Int): Int {
        return v * v
    }

    val v1 = (0..100).random()
    return foo(v1)
}

/**
 * lambda表达式:无参数情况
 */
fun test() {
    println("无参数方法")
}

val test1 = { println("lamdba表达式无参数方法") }

/**
 * lambda表达式:有参数情况
 */
fun test2(a: Int, b: Int): Int {
    return a + b
}

val test3: (Int, Int) -> Int = { a, b -> a + b }

//简化写法
val test4 = { a: Int, b: Int -> a + b }

//it的用法：lambda表达式参数只有一个的时候可以用it来作为此参数，it表示单参数的隐式名称
fun test5() {
    val arr = arrayOf(1, 2, 3, 4, 5)
    println(arr.filter {
        //it 表示数组的每一项
        it < 5
    })

    val maps2 = mapOf("k2" to "v2", "k1" to "v1", "k3" to "v3", "k4" to "v4")
    //_的用法：在kotlin中表示未使用该参数，不处理这个参数
    maps2.forEach { (_, value) ->
        println(value)
    }
}

/**
 * 高阶函数：函数作为参数
 * 对List<Int>扩展一个sum方法
 */
fun List<Int>.sum(callback: (Int) -> Unit): Int {
    var result = 0;
    //this表示扩展函数的本身List
    for (v in this) {
        result += v
        //回调集合中的每一项元素
        callback(v)
    }
    return result
}

/**
 * 高阶函数：函数作为返回值
 */
fun List<String>.toIntSum(): (scale: Int) -> Float {
    println("第一层函数")
    //返回一个函数
    return fun(scale: Int): Float {
        var result = 0f
        for (v in this) {
            result += v.toInt() * scale
        }
        return result
    }
}

/**
 * 实现一个闭包
 */
fun testCourse(v1: Int): (v2: Int, (Int) -> Unit) -> Unit {
    return fun(v2: Int, printer: (Int) -> Unit) {
        printer(v1 + v2)
    }
}

/**
 * 解构赋值
 */
data class Result(val result: String, val code: Int)

fun test6() {
    val (message, code) = Result("success", 0)
    println("message:${message},code:${code}")
}


/**
 * 匿名函数
 */
val test7 = fun(x: Int, y: Int): Int = x + y

fun test8() {
    //temp 变量 作为函数类型
    var temp: ((Int) -> Boolean?)? = null

    //方法字面值
    temp = { num ->
        num > 10
    }
    //等价于
//    temp = fun(num: Int): Boolean {
//        return num > 10
//    }
    println(temp(11))
}