package com.fbe.lib_dataitem

import java.io.Serializable

/**
 * 设置类型用在{@see MultipleAdapter.addDataTypeList() <T:MultipleType> }
 */
abstract class MultipleType : Serializable {
    /**
     * type 需要和@Item(type) 进行一一对应
     */
    abstract fun getType(): String
}
