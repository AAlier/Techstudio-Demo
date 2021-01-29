package com.techstudio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaMetadata(
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : Parcelable