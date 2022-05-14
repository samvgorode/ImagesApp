package com.samvgorode.shiftfourimages.data

import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.data.remote.ImagesResponseItem
import javax.inject.Inject

class DataMapper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun map(imageFromResponse: ImagesResponseItem): ImageEntity {
        val id = imageFromResponse.id.orEmpty()
        val favorite = sharedPreferences.getBoolean(id, false)
        return ImageEntity(
            id = id,
            url = imageFromResponse.urls?.full,
            favorite = favorite
        )
    }
}