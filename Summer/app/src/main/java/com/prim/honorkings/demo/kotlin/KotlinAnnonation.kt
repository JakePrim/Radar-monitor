package com.prim.honorkings.demo.kotlin

/**
 * @desc Kotlin的注解
 * @author sufulu
 * @time 4/6/21 - 9:27 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */

fun main() {
    fire(ArticleApi())
}

//在class前面加上annotation
@Target(AnnotationTarget.CLASS)//注解的目标
@Retention(AnnotationRetention.RUNTIME)//注解保留期
annotation class ApiDoc(val value: String)

@ApiDoc("修饰类")
class Box {
    //    @ApiDoc("修饰字段")
    val size = 6

    //    @ApiDoc("修饰方法")
    fun test() {

    }
}

/**使用注解模拟网路请求**/
public enum class Method {
    GET,
    POST
}


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
public annotation class HttpMethod(val method: Method)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class Url(val url: String)

public interface Api {
    val name: String
    val version: String
        get() = "1.0"


    fun getList()
}


@HttpMethod(Method.POST)
public class ArticleApi : Api {
    override val name: String
        get() = "api/article"

    @Url("/get/list")
    override fun getList() {
        println("发出请求")
    }
}

/**
 * 发送网络请求
 */
fun fire(api: Api) {
    //通过反射获取注解信息
    val annotations = api.javaClass.annotations
    //获取所有的方法
    val methods = api.javaClass.declaredMethods
    for (m in methods) {
        val methodAnn = m.annotations
        val url = methodAnn.find { it is Url } as? Url
        println("方法名:${m.name},方法注解@Url:${url?.url}")
    }

    val method = annotations.find { it is HttpMethod } as? HttpMethod
    println(method?.method) //@com.prim.honorkings.demo.kotlin.HttpMethod(method=GET)
}