package com.samvgorode.shiftfourimages.data

import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.data.local.FavoriteImageDao
import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.data.remote.ApiService
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ext.orFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ImagesRepositoryImpl(
    private val apiService: ApiService,
    private val imagesDao: FavoriteImageDao,
    private val dataMapper: DataMapper,
    private val sharedPreferences: SharedPreferences,
) : ImagesRepository {

    private var lastSelectedImage: ImageEntity? = null

    override suspend fun getImages(page: Int): List<ImageEntity> = withContext(Dispatchers.IO) {
        val images = apiService.getImages(page, CLIENT_ID)
        images.map(dataMapper::map)
    }

    override suspend fun getFavoriteImages(): List<ImageEntity> = withContext(Dispatchers.IO) {
        imagesDao.getAllFavorite()
    }

    override fun setLastSelectedImage(image: ImageEntity) {
        lastSelectedImage = image
    }

    override fun getLastSelectedImage() = lastSelectedImage

    override suspend fun insertImage(image: ImageEntity) = withContext(Dispatchers.IO) {
        sharedPreferences.edit().putBoolean(image.id, image.favorite.orFalse()).apply()
        imagesDao.insertImage(image)
    }

    override suspend fun getImageFavorite(id: String): Boolean = withContext(Dispatchers.Default) {
        sharedPreferences.getBoolean(id, false)
    }

    private companion object {
        // if one key inactive - use another (limit - 50 requests per hour!)
        // const val CLIENT_ID = "8OSta1KUPT104fmjBEdTOA4TRQFsULhrYsOP84Om0kM"
        const val CLIENT_ID = "OuBDDQKwsvso-cjES67ha5EUoY55Mx5F-1035mFaqr8"
    }
}