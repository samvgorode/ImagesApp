package com.samvgorode.shiftfourimages.presentation.image

sealed class ImageIntent {
    data class SetImageFavorite(val favorite: Boolean): ImageIntent()
    object GetSelectedImage: ImageIntent()
}