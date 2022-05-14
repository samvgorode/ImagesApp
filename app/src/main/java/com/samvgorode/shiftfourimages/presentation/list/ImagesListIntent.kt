package com.samvgorode.shiftfourimages.presentation.list

sealed class ImagesListIntent {
    data class GetImagesList(val page: Int): ImagesListIntent()
}