package com.samvgorode.shiftfourimages.domain

import com.samvgorode.shiftfourimages.data.local.ImageEntity

interface ImagesRepository {
    suspend fun getImages(page: Int): List<ImageEntity>
    suspend fun getFavoriteImages(): List<ImageEntity>
    fun setLastSelectedImage(image: ImageEntity)
    fun getLastSelectedImage(): ImageEntity?
    suspend fun insertImage(image: ImageEntity)
    suspend fun getImageFavorite(id: String): Boolean
}