package com.samvgorode.shiftfourimages.data

import com.samvgorode.shiftfourimages.data.local.ImageDao
import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.data.remote.ApiService
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagesRepositoryImpl(
    private val apiService: ApiService,
    private val imageDao: ImageDao,
    private val dataMapper: DataMapper
) : ImagesRepository {

    override suspend fun getImages(page: Int): List<ImageEntity> = withContext(Dispatchers.IO) {
        val images = apiService.getImages(page, LIMIT, CLIENT_ID)
        val dbImages = images.map(dataMapper::map)
        val insert = imageDao.insertImages(dbImages)
        return@withContext if (insert.isNotEmpty()) imageDao.getAll(
            LIMIT, (page - 1) * LIMIT
        ) else listOf()
    }

    private companion object {
        const val CLIENT_ID = "8OSta1KUPT104fmjBEdTOA4TRQFsULhrYsOP84Om0kM"
        const val LIMIT = 10
    }
}