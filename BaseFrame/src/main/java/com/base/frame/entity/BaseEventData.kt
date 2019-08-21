package com.base.frame.entity

import java.io.Serializable

class BaseEventData : Serializable {
    var value: Any? = null
    var valueList: MutableList<Any> = mutableListOf()
    var key: Enum<*>

    constructor(key: Enum<*>, value: Any) {
        this.key = key
        this.value = value
    }

    constructor(key: Enum<*>, list: MutableList<Any>) {
        this.key = key
        this.valueList = list
    }

    constructor(key: Enum<*>) {
        this.key = key
    }
}