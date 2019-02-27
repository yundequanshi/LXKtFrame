package com.jnevision.laibobio.common.http

import com.google.gson.Gson

interface HttpResponseInterface {
    fun getResponseData(gson: Gson, response: String): String
}