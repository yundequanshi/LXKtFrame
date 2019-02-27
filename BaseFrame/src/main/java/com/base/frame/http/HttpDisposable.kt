package com.jnevision.laibobio.common.http

import com.blankj.utilcode.util.ToastUtils
import io.reactivex.observers.DisposableObserver

abstract class HttpDisposable<T> (): DisposableObserver<T>() {


    override fun onComplete() {
    }

    override fun onNext(value: T) {
        success(value)
    }

    override fun onError(e: Throwable) {
        ToastUtils.showShort(e.message)
    }

    override fun onStart() {

    }

    abstract fun success(value: T)
}