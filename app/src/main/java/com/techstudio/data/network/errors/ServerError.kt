package com.techstudio.data.network.errors

import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("fault")
    val fault: Fault,
)

data class Fault(
    @SerializedName("faultstring")
    val faultText : String? = null,
    @SerializedName("detail")
    val detail: ErrorDetail
)

data class ErrorDetail(
    @SerializedName("errorcode")
    val errorCode: ErrorCode?,
)