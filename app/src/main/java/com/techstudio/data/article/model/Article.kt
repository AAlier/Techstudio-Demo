package com.techstudio.data.article.model

import com.google.gson.annotations.SerializedName
import com.techstudio.model.MediaType
import java.util.*

data class Article(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("abstract")
    val info: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("updated")
    val updated: Calendar,
    @SerializedName("media")
    val media: List<Media>
)

data class Media(
    @SerializedName("type")
    val type: MediaType,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadata>
)

data class MediaMetadata(
    @SerializedName("url")
    val url: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)
