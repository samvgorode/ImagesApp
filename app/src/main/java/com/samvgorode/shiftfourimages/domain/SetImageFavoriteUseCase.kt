package com.samvgorode.shiftfourimages.domain

import android.content.SharedPreferences
import javax.inject.Inject

class SetImageFavoriteUseCase @Inject constructor(private val sharedPreferences: SharedPreferences) {

    operator fun invoke(id: String, favorite: Boolean) {
        sharedPreferences.edit().putBoolean(id, favorite).apply()
    }
}