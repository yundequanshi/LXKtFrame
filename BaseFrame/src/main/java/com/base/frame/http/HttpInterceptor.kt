package com.base.frame.http

import android.util.Log.d
import com.blankj.utilcode.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

class HttpInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")
    private val REQUEST_TAG = "请求"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isConnected()) {
            throw HttpException("网络连接异常，请检查网络后重试")
        }
        var request: Request = chain.request()
        val response: Response = chain.proceed(request)
        logRequest(request)
        logResponse(response)
        return response
    }

    private fun logResponse(response: Response) {
        try {
            val responseBody: ResponseBody = response.body()!!
            var rBody: String?
            val source: BufferedSource = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer: Buffer = source.buffer()
            var charset: Charset = UTF8
            val contentType: MediaType = responseBody.contentType()!!
            try {
                charset = contentType.charset(UTF8)!!
            } catch (e: UnsupportedCharsetException) {
                e.printStackTrace()
            }
            rBody = buffer.clone().readString(charset)
            d(REQUEST_TAG + "返回值", rBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun logRequest(request: Request) {
        d(REQUEST_TAG + "URL", request.url().toString())
        val cookie: String? = request.header("Cookie")
        cookie?.let {
            d(REQUEST_TAG + "COOKIE", it)
        }
        if (request.method() == "GET") {
            return
        }
        try {
            val requestBody: RequestBody = request.body()!!
            var parameter: String?
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            parameter = buffer.readString(UTF8)
            buffer.flush()
            buffer.close()
            d(REQUEST_TAG + "参数", parameter)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}