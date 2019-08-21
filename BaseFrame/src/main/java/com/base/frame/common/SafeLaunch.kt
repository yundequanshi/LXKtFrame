package com.base.frame.common

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * 解决协程处理网络请求不能处理异常
 */
private val loggingExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            Log.d("请求", throwable.message)
            ToastUtils.showShort(throwable.message)
        }
    }

private val handlerContext: CoroutineContext = loggingExceptionHandler + GlobalScope.coroutineContext

fun CoroutineScope.safeLaunch(block: suspend () -> Unit): Job = launch(handlerContext) { block() }