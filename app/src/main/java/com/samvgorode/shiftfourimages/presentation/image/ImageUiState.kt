package com.samvgorode.shiftfourimages.presentation.image

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

data class ImageUiState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val image: ImageUiModel = ImageUiModel()
)