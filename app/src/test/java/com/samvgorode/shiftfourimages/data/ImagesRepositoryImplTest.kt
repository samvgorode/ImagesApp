package com.samvgorode.shiftfourimages.data

import com.samvgorode.shiftfourimages.data.local.ImageDao
import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.data.remote.ApiService
import com.samvgorode.shiftfourimages.data.remote.ImagesResponseItem
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImagesRepositoryImplTest {

    @Test
    fun `getImages should return images list`() = runTest {
        val imagesList = listOf<ImageEntity>(mockk(), mockk(), mockk())
        val apiService = getApiService()
        val dataMapper = getDataMapper()
        val imagesDao = getImageDao(output = imagesList)
        val repositoryImpl = ImagesRepositoryImpl(apiService, imagesDao, dataMapper)
        val images = repositoryImpl.getImages(1)

        coVerify(exactly = 1) { apiService.getImages(any(), any(), any()) }
        verify(exactly = 1) { dataMapper.map(any()) }
        coVerify { imagesDao.getAll(any(), any()) }

        assertEquals(images, imagesList)
    }

    private fun getApiService(output: ImagesResponseItem = mockk()) = mockk<ApiService> {
        coEvery { getImages(any(), any(), any()) } returns listOf(output)
    }

    private fun getDataMapper(
        output: ImageEntity = mockk()
    ) = mockk<DataMapper> {
        every { map(any()) } returns output
    }

    private fun getImageDao(
        input: ImageEntity = mockk(),
        output: List<ImageEntity> = listOf()
    ) = mockk<ImageDao> {
        coEvery { getAll(any(), any()) } returns output
        coEvery { insertImages(any()) } returns listOf(1L)
    }
}