package com.techstudio.data.network.errors

import android.content.Context
import com.techstudio.R
import java.io.IOException

class ServerException(
    val statusCode: Int,
    private val serverError: ServerError
) : IOException("statusCode: $statusCode, errorMessage: $serverError") {
    fun getErrorMessage(context: Context) =
        serverError.fault.faultText ?: context.getString(R.string.general_error_message)
}
