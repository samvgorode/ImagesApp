package com.samvgorode.shiftfourimages.domain.lastSelected

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

interface GetSelectedImageUseCase {
    operator fun invoke(): ImageUiModel?
}