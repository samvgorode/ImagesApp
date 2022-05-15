package com.samvgorode.shiftfourimages.domain.favorite

import com.samvgorode.shiftfourimages.domain.ImagesRepository

internal class GetImageFavoriteUseCaseImpl(private val repository: ImagesRepository) :
    GetImageFavoriteUseCase {

    override suspend operator fun invoke(id: String): Boolean =
        repository.getImageFavorite(id)
}