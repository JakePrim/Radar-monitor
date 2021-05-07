package com.prim.honorkings.demo.kotlin

import java.util.*
import kotlin.collections.ArrayList

/**
 * @desc
 * @author sufulu
 * @time 4/6/21 - 5:23 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
fun main() {
//    Dog(11)
//    val shop = Shop()
//    println(shop.isClose)

//    val t = Test()
//    t.test()
//    t.setup()
//    t.test()//true

    test3()
}

/**
 * kotlin的类与接口
 * constructor:定义主构造方法 constructor可以被隐藏
 */
class KotlinClass constructor(name: String) {
    /**
     * 次构造方法，必须调用主构造方法
     * 可以定义多个次构造方法
     */
    constructor(name: String, age: Int) : this(name) {
        println("name:${name},age:${age}")
    }

    constructor(name: String, sex: String) : this(name) {
        println("name:${name},sex:${sex}")
    }
}

/**
 * Animal类要想被继承默认是final修饰的必须使用open修饰
 * (age: Int) 是主构造方法
 */
open class Animal(age: Int) {
    //初始化静态代码块
    init {
        println(age)
    }

    /**
     * 属性默认也是隐藏的
     */
    open var foot: Int = 0

    /**
     * 方法默认是隐藏的
     * 默认不允许覆盖，使用open关键词就可以允许覆盖该方法
     */
    open fun eat() {

    }
}

class Dog(age: Int) : Animal(age) {

    val simple: Int? = null

    /**
     * 覆盖父类的属性
     */
    override var foot = 10

    /**
     * 覆盖父类方法需要override关键字
     */
    override fun eat() {
        foot = 1;
        //调用父类方法
        super.eat()
    }
}

//也可以这样定义 构造方法
class Dog2 : Animal {
    constructor(age: Int) : super(age)
}

//--------------setter和getter的使用----------------//
class Shop {
    val name: String = "Android"
    val address: String? = null
    val isClose: Boolean
        get() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 11
    var score: Float = 0.0f
        get() {
            if (field < 0.2f) return 0.2f
            else return field * 1.5f
        }
        set(value) {
            println(value)
        }
}

//-------------延迟初始化属性--------------------//
class Test {
    lateinit var shop: Shop

    /**
     * 在该方法中才会初始化shop属性
     */
    fun setup() {
        shop = Shop()
    }

    /**
     * 判断属性是否初始化了
     */
    fun test() {
        //::表示创建成员引用或类引用
        if (::shop.isInitialized) {//返回true表示初始化成功了
            println(shop.isClose)
        }
    }
}

/**
 * 定义抽象类
 */
abstract class Printer {
    abstract fun print()
}

/**
 * 实现抽象类
 */
class FilePrinter : Printer() {
    override fun print() {
        println("print file")
    }
}

/**
 * 定义接口类
 */
interface Study {
    val time: Int//接口定义的抽象的属性

    fun discuss()

    //kotlin接口可以有自己的实现
    fun learn() {
        println("android")
    }
}

/**
 * 实现接口
 * time 需要实现接口定义的属性
 */
class StudyAs(override val time: Int) : Study {
    override fun discuss() {
        println("discuss")
    }

    override fun learn() {
        super.learn()
    }
}

interface A {
    fun foo() {
        println("A.foo")
    }
}

interface B {
    fun foo() {
        println("B.foo")
    }
}

class D : A, B {
    /**
     * 接口A和接口B都有foo方法
     */
    override fun foo() {
        super<A>.foo()//调用接口A的foo方法
    }

}

/**
 * 数据类
 * 数据类不能抽象和继承的
 */
data class Address(var name: String, var number: Int) {
    val city: String = ""
    fun print() {
        println(city)
    }
}

//-----------对象表达式---------------//
/**
 * 存在如下类，open开放 可以被继承
 */
open class School(var name: String) {
    open fun print() {

    }
}

class Shop2 {
    var school: School? = null

    fun add(school: School) {
        this.school = school
    }

    fun print() {
        school?.print()
    }
}

fun test3() {
    //假设school类需要继承修改print方法，按照java来说就需要创建一个类去继承School然后重写print
    //但是在kotlin中有对象表达式 不用再去创建一个类
    val shop2 = Shop2()
    shop2.add(object : School("Kotlin") {
        override fun print() {
            println("school name:${name}")
        }
    })

    shop2.print()//school name:Kotlin

    //kotlin还支持创建一个简单的对象 注意需要用到object关键字 匿名对象声明
    var addHoc = object {
        var x: Int = 0
        var y: Int = 1
        fun print() {
            println("X:${x},Y:${y}")
        }
    }

    addHoc.print()//X:0,Y:1

    val arr: ArrayList<String> = arrayListOf()
    println(DataUtil.isEmpty(arr))//true

    //调用静态属性和静态方法
    println(Student.student)
    Student.study()
}

/**
 * object 声明的类都是静态的属性和方法
 * 类似java中的单例的实现
 */
object DataUtil {
    fun <T> isEmpty(list: ArrayList<T>?): Boolean {
        return list?.isEmpty() ?: false
    }
}



class Student(var name: String) {
    companion object {
        val student: Student = Student("Android")
        fun study() {
            println("Android .....")
        }
    }
}


