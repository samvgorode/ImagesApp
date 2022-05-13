package com.samvgorode.shiftfourimages.data

import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.data.remote.ImagesResponseItem
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun map(imageFromResponse: ImagesResponseItem) = ImageEntity(
        id = imageFromResponse.id.orEmpty(),
        url = imageFromResponse.urls?.full
    )
}