package com.samvgorode.shiftfourimages.domain

import com.samvgorode.shiftfourimages.data.local.ImageEntity

interface ImagesRepository {
    suspend fun getImages(page: Int): List<ImageEntity>
}