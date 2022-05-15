package com.samvgorode.shiftfourimages.domain.favorite

import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.domain.favorite.SetImageFavoriteUseCase

internal class GetImageFavoriteUseCaseImpl(private val sharedPreferences: SharedPreferences) :
    GetImageFavoriteUseCase {

    override operator fun invoke(id: String): Boolean =
        sharedPreferences.getBoolean(id, false)
}