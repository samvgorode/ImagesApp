package com.samvgorode.shiftfourimages.presentation.list

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

sealed class ImagesListIntent {
    data class GetImagesList(val page: Int) : ImagesListIntent()
    object Refresh : ImagesListIntent()
    data class SetImageFavorite(val id: String, val favorite: Boolean) : ImagesListIntent()
    data class SetImageSelected(val image: ImageUiModel) : ImagesListIntent()
    object RefreshLastSelectedImage : ImagesListIntent()
}