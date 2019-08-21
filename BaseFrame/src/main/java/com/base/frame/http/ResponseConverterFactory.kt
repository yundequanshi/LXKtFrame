package com.base.frame.http

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class ResponseConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return BaseResponseBodyConverter<Any>(type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return GsonConverterFactory.create()
            .requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    private inner class BaseResponseBodyConverter<T>(private val mType: Type) : Converter<ResponseBody, T> {
        override fun convert(response: ResponseBody): T {
            val mGson: Gson = GsonBuilder().setLenient().create()
            var objectData: T?
            try {
                val strResponse: String = response.string()
                if (TextUtils.isEmpty(strResponse)) {
                    throw HttpException("返回值是空的—-—")
                }
                if (HttpFactory.httpResponseInterface == null) {
                    throw HttpException("请实现接口HttpResponseInterface—-—")
                } else {
                    val strData: String = HttpFactory.httpResponseInterface!!.getResponseData(mGson, strResponse)
                    objectData = mGson.fromJson(strData, mType)
                }
            } catch (e: Exception) {
                throw HttpException(e.message!!)
            } finally {
                response.close()
            }
            return objectData!!
        }
    }
}