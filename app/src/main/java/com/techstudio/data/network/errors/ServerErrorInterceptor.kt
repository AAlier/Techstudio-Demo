package com.techstudio.data.network.errors

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import okhttp3.Interceptor
import okhttp3.Response

class ServerErrorInterceptor(
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.isSuccessful) {
            return response
        } else {
            throw extractException(response)
        }
    }

    private fun extractException(response: Response): Throwable {
        val statusCode = response.code()
        return try {
            val serverError = response.body()!!.use {
                gson.fromJson<ServerError>(JsonReader(it.charStream()), ServerError::class.java)
            }

            ServerException(
                statusCode = statusCode,
                serverError = serverError
            )
        } catch (e: Throwable) {
            ServerException(
                statusCode = statusCode,
                serverError = ServerError(
                    fault = Fault(
                        detail = ErrorDetail(ErrorCode.Error)
                    )
                )
            )
        }
    }
}
