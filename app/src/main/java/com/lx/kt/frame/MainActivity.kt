package com.lx.kt.frame

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        request()
    }

    /**
     * 协程请求
     */
    fun request() {

    }
}
