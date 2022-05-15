package com.samvgorode.shiftfourimages.domain.favorite

interface GetImageFavoriteUseCase {
    suspend operator fun invoke(id: String): Boolean
}