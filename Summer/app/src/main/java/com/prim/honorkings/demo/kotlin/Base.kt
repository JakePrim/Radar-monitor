package com.prim.honorkings.demo.kotlin

import kotlin.math.abs

/**
 * @desc kotlin 学习
 * @author prim
 * @time 3/26/21 - 9:56 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 * @version 1.0.0
 */
fun main() {
    println("--------main---------")
//    baseType()
//    arrayType()
//    collectionType()
//    collectionSort()
//    collectionSet()
    collectionMap()
}

fun collectionMap() {
    val maps = mapOf("k1" to "v1", "k2" to "v2", "k3" to "v3", "k4" to "v4")
    println("All keys: ${maps.keys},${maps.get("k1")}")
    println("All values: ${maps.values}")
    //判断key是否存在
    if ("k2" in maps) {
        println("value by k2:${maps["k2"]}")//value by k2:v2
    }
    //判断value是否存在
    if ("v1" in maps.values) {
        println("v1 by maps")//v1 by maps
    }

    if (maps.containsKey("k3")) {
        println("k3 in maps")
    }
    if (maps.containsValue("v3")) {
        println("v2 in maps")
    }
    /**
     * Q1: 两个map具有相同的键值对，但顺序不同 map相等吗？
     * A1: 无论两个map键值对的顺序如何，包含相同键值对的map就是相等的
     */
    val maps2 = mapOf("k2" to "v2", "k1" to "v1", "k3" to "v3", "k4" to "v4")
    println(maps == maps2)//true 等价于 equals方法

    /**
     * Q2:两个具有相同元素，但是顺序不同的list相等吗？
     * A2:因为list和map不同，list.get(0) != list2.get(0) 而 map.get("k1") == map2.get("k1")的
     */
    val list1 = mutableListOf(1, 2, 3, 4)
    val list2 = mutableListOf(2, 1, 4, 3)
    println(list1 == list2)//false
}

fun collectionSet() {
    val sets = mutableSetOf("H", "e", "l", "l", "o") //自动过滤重复元素
    sets.remove("o")
    println(sets)

    //集合加减操作 sets必须是可变的set集合
    sets += setOf<String>("w", "o", "r", "l", "d")
    println(sets)
}

/**
 * 集合排序
 */
fun collectionSort() {
    val arr = mutableListOf(1, 2, 3, 4)
    //随机排序
    arr.shuffle()
    println(arr)
    //从小到大排序
    arr.sort()
    println(arr)
    //从大到小排序
    arr.sortDescending()
    println(arr)

    //条件排序
    //定义数据类
    data class Language(var name: String, var score: Int)

    var languageList: MutableList<Language> = mutableListOf()
    languageList.add(Language("Java", 80))
    languageList.add(Language("C", 80))
    languageList.add(Language("Kotlin", 90))
    languageList.add(Language("Dart", 99))
    languageList.sortBy { -it.score }//根据score从小到大排序   -score从大到小排序
    println(languageList)

    //sortWith多条件排序   先根据name排序 在根据分数排序
    languageList.sortWith(compareBy({ it.name }, { it.score }))
    println(languageList)
}

/**
 * 集合
 */
fun collectionType() {
    //不可变集合
    val stringList = listOf("one", "two", "three")
    //没有add remove方法 只能get获取
    println(stringList)
    //setOf 也是不可变集合 或删除重复项
    val stringSet = setOf("one", "two", "one")
    println(stringSet)

    //可变结合
    val numbers = mutableListOf(1, 2, 3, 4)
    numbers.add(5)//添加到元素的末尾
    numbers.add(1, 6)//中间插入某个元素
    numbers.removeAt(0)//移除下标为0的元素
    println(numbers)

}

/**
 * 数组
 */
fun arrayType() {
    //快速创建数组
    var arr = arrayOf(1, 2, 3)

    //快速创建一个固定大小的数组
    var arr2 = arrayOfNulls<Int>(3)
    arr2[0] = 4
    arr2[1] = 5
    arr2[2] = 6

    //Array() 构造函数的形式
    val arr3 = Array(5) { i -> (i * i).toString() }

    //快速创建某种类型的数组
//    doubleArrayOf()
//    floatArrayOf()
    val arr4 = intArrayOf(1, 2, 3)
    println("${arr4[1]}")

    //指定大小的类型的数组
    val arr5 = IntArray(5) { 4 } //5个元素都是4【4，4，4，4，4】
    println(arr5[2])

    //使用lamda表达式初始化数组的值
    val arr6 = IntArray(5) { it * 1 } //[0,1,2,3,4]

    println(arr6[3])

    //遍历数组常用的5种方式

    //数组遍历
    for (item in arr) {
        println(item)
    }

    //带索引遍历数组
    for (i in arr.indices) {
        println("$i -> ${arr[i]}")
    }

    //遍历元素
    for ((index, item) in arr.withIndex()) {
        println("$index -> $item")
    }

    //forEach 方式遍历数组
    arr.forEach { println(it) }

    //forEach 增强版
    arr.forEachIndexed { index, item -> println("$index -> $item") }
}

/**
 * 基础的数据类型
 */
fun baseType() {
    //val 表示不可变的变量
    val num1 = -1.68//Double 自动推断数据类型

    val num2 = 2// Int

    val num3 = 2f //Float

    val int1 = 3

    println("num1:$num1，$num2,$num3,$int1")

    println(num1.toInt()) //转换数据类型
    println(abs(num1))//绝对值

    printType(num1)
    printType(num2)
    printType(num3)

}

fun printType(param: Any) {
    println("$param is ${param::class.simpleName} type")
}
