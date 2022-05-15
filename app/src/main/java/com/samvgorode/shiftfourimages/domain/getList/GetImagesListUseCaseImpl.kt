package com.samvgorode.shiftfourimages.domain.getList

import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

internal class GetImagesListUseCaseImpl(
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper
) : GetImagesListUseCase {

    override suspend operator fun invoke(page: Int): List<ImageUiModel> {
        val dbCities = repository.getImages(page)
        return dbCities.map(mapper::mapToUi)
    }
}