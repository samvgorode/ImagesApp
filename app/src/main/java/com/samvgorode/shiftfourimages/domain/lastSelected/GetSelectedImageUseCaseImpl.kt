package com.samvgorode.shiftfourimages.domain.lastSelected

import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

internal class GetSelectedImageUseCaseImpl(
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper
) : GetSelectedImageUseCase {
    override operator fun invoke(): ImageUiModel? =
        repository.getLastSelectedImage()?.let(mapper::mapToUi)
}