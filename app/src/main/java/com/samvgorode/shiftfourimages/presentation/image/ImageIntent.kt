package com.samvgorode.shiftfourimages.presentation.image

sealed class ImageIntent {
    data class SetImageFavorite(val id: String, val favorite: Boolean): ImageIntent()
    object GetSelectedImage: ImageIntent()
}