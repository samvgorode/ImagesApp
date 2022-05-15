package com.samvgorode.shiftfourimages.domain.lastSelected

import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

internal class SetSelectedImageUseCaseImpl(
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper
) : SetSelectedImageUseCase {
    override operator fun invoke(image: ImageUiModel) {
        repository.setLastSelectedImage(image.let(mapper::mapToDomain))
    }
}