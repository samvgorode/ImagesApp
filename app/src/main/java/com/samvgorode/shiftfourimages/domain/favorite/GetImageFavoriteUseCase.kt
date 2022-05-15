package com.samvgorode.shiftfourimages.domain.favorite

interface GetImageFavoriteUseCase {
    operator fun invoke(id: String): Boolean
}