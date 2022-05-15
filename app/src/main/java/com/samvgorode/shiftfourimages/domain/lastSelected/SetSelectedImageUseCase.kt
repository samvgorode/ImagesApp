package com.samvgorode.shiftfourimages.domain.lastSelected

import com.samvgorode.shiftfourimages.presentation.ImageUiModel

interface SetSelectedImageUseCase {
    operator fun invoke(image: ImageUiModel)
}