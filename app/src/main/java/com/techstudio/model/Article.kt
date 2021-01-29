package com.techstudio.model

import java.util.*

data class Article(
    val id: Long,
    val title: String,
    val info: String,
    val source: String,
    val updated: Calendar,
    val media: List<Media>
) {

    var isFavourite: Boolean = false

    val thumbnail: String
        get() = media.lastOrNull()?.mediaMetadata?.lastOrNull()?.url.orEmpty()
}