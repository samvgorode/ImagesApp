package com.samvgorode.shiftfourimages.data

import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.data.local.FavoriteImageDao
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
        val response =  listOf<ImagesResponseItem>(mockk(), mockk(), mockk())
        val apiService = getApiService(response)
        val dataMapper = getDataMapper(response, imagesList)
        val imageDao: FavoriteImageDao = mockk()
        val sp: SharedPreferences = mockk()
        val repositoryImpl = ImagesRepositoryImpl(apiService, imageDao, dataMapper, sp)
        val images = repositoryImpl.getImages(1)

        coVerify(exactly = 1) { apiService.getImages(any(),any()) }
        verify(exactly = imagesList.size) { dataMapper.map(any()) }

        assertEquals(images, imagesList)
    }

    private fun getApiService(output: List<ImagesResponseItem>) = mockk<ApiService> {
        coEvery { getImages(any(), any()) } returns output
    }

    private fun getDataMapper(
        input: List<ImagesResponseItem>,
        output: List<ImageEntity>
    ) = mockk<DataMapper> {
        if(input.size == output.size) input.forEachIndexed { index, image ->
            every { map(image) } returns output[index]
        }
    }
}