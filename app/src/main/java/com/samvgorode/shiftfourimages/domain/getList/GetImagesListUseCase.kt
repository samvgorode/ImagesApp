package com.samvgorode.shiftfourimages.domain.getList

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

interface GetImagesListUseCase {
    suspend operator fun invoke(page: Int): List<ImageUiModel>
}