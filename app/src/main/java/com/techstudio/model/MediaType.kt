package com.techstudio.model

import com.google.gson.annotations.SerializedName

// TODO handle other media types such as VIDEO, TEXT
enum class MediaType {
    @SerializedName("image")
    IMAGE,
}