package com.samvgorode.shiftfourimages.presentation.list

sealed class ImagesListIntent {
    data class GetImagesList(val page: Int): ImagesListIntent()
    object Refresh: ImagesListIntent()
    data class SetImageFavorite(val id: String, val favorite: Boolean): ImagesListIntent()
}