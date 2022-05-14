package com.samvgorode.shiftfourimages.data

import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.data.remote.ApiService
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagesRepositoryImpl(
    private val apiService: ApiService,
    private val dataMapper: DataMapper
) : ImagesRepository {

    override suspend fun getImages(page: Int): List<ImageEntity> = withContext(Dispatchers.IO) {
        val images = apiService.getImages(page, LIMIT, CLIENT_ID)
        return@withContext images.map(dataMapper::map)
    }

    private companion object {
        const val CLIENT_ID = "8OSta1KUPT104fmjBEdTOA4TRQFsULhrYsOP84Om0kM"
        const val LIMIT = 10
    }
}