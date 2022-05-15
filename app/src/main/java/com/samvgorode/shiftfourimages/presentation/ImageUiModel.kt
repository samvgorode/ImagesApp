package com.samvgorode.shiftfourimages.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUiModel(
    val id: String = "",
    val url: String = "",
    val favorite: Boolean = false,
) : Parcelable