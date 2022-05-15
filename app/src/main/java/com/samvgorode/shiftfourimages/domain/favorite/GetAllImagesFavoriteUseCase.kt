package com.samvgorode.shiftfourimages.domain.favorite

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

interface GetAllImagesFavoriteUseCase {
    suspend operator fun invoke(): List<ImageUiModel>
}