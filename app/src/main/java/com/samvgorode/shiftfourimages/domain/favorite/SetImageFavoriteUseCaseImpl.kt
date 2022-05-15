package com.samvgorode.shiftfourimages.domain.favorite

import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.domain.favorite.SetImageFavoriteUseCase

internal class SetImageFavoriteUseCaseImpl(private val sharedPreferences: SharedPreferences) :
    SetImageFavoriteUseCase {

    override operator fun invoke(id: String, favorite: Boolean) {
        sharedPreferences.edit().putBoolean(id, favorite).apply()
    }
}