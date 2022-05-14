package com.samvgorode.shiftfourimages.domain

import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import javax.inject.Inject

class ImagesDomainMapper @Inject constructor() {

    fun map(imageEntity: ImageEntity): ImageUiModel {
        return ImageUiModel(
            id = imageEntity.id,
            url = imageEntity.url.orEmpty(),
            favorite = imageEntity.favorite ?: false
        )
    }
}