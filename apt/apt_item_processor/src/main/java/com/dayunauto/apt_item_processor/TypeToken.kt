package com.dayunauto.apt_item_processor

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

fun getType(raw: Class<*>, vararg args: Type) = object : ParameterizedType {
    override fun getRawType(): Type = raw
    override fun getActualTypeArguments(): Array<out Type> = args
    override fun getOwnerType(): Type? = null
}