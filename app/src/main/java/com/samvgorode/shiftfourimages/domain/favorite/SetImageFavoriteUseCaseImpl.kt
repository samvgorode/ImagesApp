package com.samvgorode.shiftfourimages.domain.favorite

import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

internal class SetImageFavoriteUseCaseImpl(
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper,
) : SetImageFavoriteUseCase {

    override suspend operator fun invoke(imageUiModel: ImageUiModel) {
        repository.insertImage(imageUiModel.let(mapper::mapToDomain))
    }
}