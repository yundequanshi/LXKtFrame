package com.base.frame.http

import okhttp3.RequestBody
import okhttp3.MultipartBody
import android.text.TextUtils
import okhttp3.MediaType
import java.io.File

object UploadUtils {
    private val FILE_NOT_NULL = "文件不能为空"
    private val FILE_PATH_NOT_NULL = "文件路径不能为空"

    fun getMultipartBody(path: String, partName: String): MultipartBody.Part {
        if (TextUtils.isEmpty(path)) throw NullPointerException(FILE_PATH_NOT_NULL)
        val file = File(path)
        if (file.exists()) {
            val requestFile: RequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile)
        } else {
            throw NullPointerException(FILE_NOT_NULL)
        }
    }

    fun getMultipartBody(file: File, partName: String): MultipartBody.Part {
        if (file.exists()) {
            val requestFile: RequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile)
        } else {
            throw NullPointerException(FILE_NOT_NULL)
        }
    }

    fun getMultipartBodysForFile(files: List<File>, partName: String): List<MultipartBody.Part> {
        if (files.isEmpty()) throw NullPointerException(FILE_NOT_NULL)
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        for (file: File in files) {
            if (file.exists()) {
                val requestFile: RequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
                builder.addFormDataPart(partName, file.getName(), requestFile)
            } else {
                throw NullPointerException(FILE_NOT_NULL)
            }
        }
        return builder.build().parts()
    }

    fun getMultipartBodysForPath(paths: List<String>, partName: String): List<MultipartBody.Part> {
        if (paths.isEmpty()) throw NullPointerException(FILE_PATH_NOT_NULL)
        val builder: MultipartBody.Builder = MultipartBody.Builder()
        for (path: String in paths) {
            val file = File(path)
            if (file.exists()) {
                val requestFile: RequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
                builder.addFormDataPart(partName, file.getName(), requestFile)
            } else {
                throw NullPointerException(FILE_NOT_NULL)
            }
        }
        return builder.build().parts()
    }
}