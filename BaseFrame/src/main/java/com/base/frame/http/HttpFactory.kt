package com.base.frame.http

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

object HttpFactory {
    var HTTP_HOST_URL = ""
    var httpResponseInterface: HttpResponseInterface? = null
    var HTTP_CLIENT = Builder().addInterceptor(HttpInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private var retrofit: Retrofit? = null

    fun <T> getChangeUrlInstance(url: String, service: Class<T>): T {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(ResponseConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(HTTP_CLIENT)
            .build()
            .create(service)
    }

    fun <T> getInstance(service: Class<T>): T {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(HTTP_HOST_URL)
                .addConverterFactory(ResponseConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HTTP_CLIENT)
                .build()
        }
        return retrofit!!.create(service)
    }

    fun <T> getInstanceCoroutine(service: Class<T>): T {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(HTTP_HOST_URL)
                .addConverterFactory(ResponseConverterFactory())
                .client(HTTP_CLIENT)
                .build()
        }
        return retrofit!!.create(service)
    }

    fun <T> schedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer<T, T> { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}