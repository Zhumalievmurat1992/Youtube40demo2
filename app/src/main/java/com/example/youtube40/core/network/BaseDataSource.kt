package com.example.youtube40.core.network

import com.example.youtube40.core.network.status.Resource
import retrofit2.Response

abstract class BaseDataSource {

    // suspend stops and resumes thread when necessary
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            } else {
                return Resource.error(response.message(), response.body())
            }
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString(), null)
        }
        return Resource.error(null, null)
    }
}