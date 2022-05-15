package com.samvgorode.shiftfourimages.domain.favorite

import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

internal class GetAllImagesFavoriteUseCaseImpl(
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper
) : GetAllImagesFavoriteUseCase {

    override suspend fun invoke(): List<ImageUiModel> =
        repository.getFavoriteImages().map(mapper::mapToUi)
}