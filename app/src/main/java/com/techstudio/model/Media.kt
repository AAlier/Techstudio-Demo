package com.techstudio.model

data class Media(
    val type: MediaType,
    val copyright: String,
    val mediaMetadata: List<MediaMetadata>
)