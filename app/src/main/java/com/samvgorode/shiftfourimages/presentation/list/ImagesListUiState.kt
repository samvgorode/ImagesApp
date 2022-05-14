package com.samvgorode.shiftfourimages.presentation.list

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

data class ImagesListUiState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val images: List<ImageUiModel> = listOf()
)