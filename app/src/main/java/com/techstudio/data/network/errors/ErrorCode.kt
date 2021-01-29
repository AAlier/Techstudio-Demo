package com.techstudio.data.network.errors

import com.google.gson.annotations.SerializedName
import com.techstudio.R

private val DEFAULT_MESSAGE_RES = R.string.general_error_message

enum class ErrorCode(val messageRes: Int = DEFAULT_MESSAGE_RES) {
    @SerializedName("ERROR")
    Error,
    @SerializedName("steps.oauth.v2.FailedToResolveAPIKey")
    WRONG_API_KEY;
}
