package com.techstudio.model

import com.techstudio.data.article.model.Article
import com.techstudio.data.article.model.Media
import com.techstudio.data.article.model.MediaMetadata

fun Article.toLocal() = com.techstudio.model.Article(
    id, title, info, source, updated, media.map { it.toLocal() }
)

fun Media.toLocal() = com.techstudio.model.Media(
    type = type,
    copyright = copyright,
    mediaMetadata = mediaMetadata.map { it.toLocal() }
)

fun MediaMetadata.toLocal() = com.techstudio.model.MediaMetadata(
    url, format, height, width
)
