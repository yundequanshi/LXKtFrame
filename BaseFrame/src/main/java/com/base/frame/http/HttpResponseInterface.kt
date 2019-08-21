package com.base.frame.http

import com.google.gson.Gson

interface HttpResponseInterface {
    fun getResponseData(gson: Gson, response: String): String
}