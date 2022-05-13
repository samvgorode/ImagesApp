package com.samvgorode.shiftfourimages.domain

import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import javax.inject.Inject

class GetImagesListUseCase @Inject constructor(
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper
) {

    suspend operator fun invoke(page: Int): List<ImageUiModel> {
        val dbCities = repository.getImages(page)
        return dbCities.map(mapper::map)
    }
}