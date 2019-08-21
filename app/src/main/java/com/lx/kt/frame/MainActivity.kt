package com.lx.kt.frame

import android.os.Bundle
import com.base.frame.common.CommonActivity
import com.base.frame.common.safeLaunch
import com.base.frame.http.HttpFactory
import com.base.frame.http.HttpResponseInterface
import com.google.gson.Gson
import com.laibobio.expert.common.http.HttpResponseData
import kotlinx.android.synthetic.main.activity_main.tvLogin

class MainActivity : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setHttpConfig()
        safeLaunch {
            val loginData: LoginData = HttpFactory.getInstanceCoroutine(API::class.java).loginCheckCode(
                mapOf(
                    "name" to "15098933621",
                    "pass" to "a12345"
                )
            )
            tvLogin.text = loginData.toString()
        }
    }

    /**
     *  请求配置设置
     */
    private fun setHttpConfig() {
        HttpFactory.HTTP_HOST_URL = "http://58.59.8.83:8096/"
        HttpFactory.httpResponseInterface = object : HttpResponseInterface {
            override fun getResponseData(gson: Gson, response: String): String {
                val objectData = gson.fromJson(response, HttpResponseData::class.java)
                var resString: String = response
                when (objectData.status) {
                    200 -> {
                        when (objectData.code) {
                            0 -> {
                                resString = gson.toJson(objectData.data)
                            }
                            1 -> {
                                //错误信息
                                throw Exception(objectData.usermessage)
                            }
                            2 -> {

                            }
                        }
                    }
                    else -> {
                        throw Exception(objectData.usermessage)
                    }
                }
                return resString
            }
        }
    }
}
