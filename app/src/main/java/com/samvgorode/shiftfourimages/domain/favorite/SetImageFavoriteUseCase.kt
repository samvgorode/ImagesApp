package com.samvgorode.shiftfourimages.domain.favorite

interface SetImageFavoriteUseCase {
    operator fun invoke(id: String, favorite: Boolean)
}