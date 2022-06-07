package com.dayunauto.apt_item_create

@Target(AnnotationTarget.CLASS) // 作用在类上
@Retention(AnnotationRetention.RUNTIME) // 存活时间是运行时
annotation class ItemAuto(val type: String)
