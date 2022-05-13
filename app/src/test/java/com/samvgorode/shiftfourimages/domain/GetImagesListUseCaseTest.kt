package com.samvgorode.shiftfourimages.domain

import com.samvgorode.shiftfourimages.TestStubs
import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetImagesListUseCaseTest {

    @Test
    fun `invoke should return proper value`() = runTest {
        val imagesDb = mockk<ImageEntity>()
        val imagesUi = mockk<ImageUiModel>()
        val imagesDbList = listOf(imagesDb)
        val imagesUiList = listOf(imagesUi)
        val repository: ImagesRepository = TestStubs.getImagesRepository(output1 = imagesDbList)
        val mapper = TestStubs.getDomainMapper(imagesDbList, imagesUiList)
        val useCase = GetImagesListUseCase(repository, mapper)
        val result = useCase.invoke(1)
        coVerify { repository.getImages(1) }
        verify(exactly = 1) { mapper.map(imagesDb) }
        assertEquals(result.first(), imagesUi)
    }

    @Test
    fun `invoke should return proper list size`() = runTest {
        val imagesList = listOf<ImageEntity>(mockk(), mockk(), mockk())
        val imagesUiList = listOf<ImageUiModel>(mockk(), mockk(), mockk())
        val repository: ImagesRepository = TestStubs.getImagesRepository(output1 = imagesList)
        val mapper = TestStubs.getDomainMapper(imagesList, imagesUiList)
        val useCase = GetImagesListUseCase(repository, mapper)
        val result = useCase.invoke(1)
        coVerify { repository.getImages(1) }
        verify(exactly = 3) { mapper.map(any()) }
        assertEquals(result.size, imagesList.size)
    }

    @Test
    fun `invoke should throw error if repository fails`() = runTest {
        val error = mockk<Throwable>()
        val repository: ImagesRepository = TestStubs.getImagesRepositoryError(output = error)
        val mapper = TestStubs.getDomainMapper()
        val useCase = GetImagesListUseCase(repository, mapper)
        try {
            useCase.invoke(1)
        } catch (e: Throwable) {
            assertEquals(e, error)
        }
        coVerify { repository.getImages(1) }
        verify(exactly = 0) { mapper.map(any()) }
    }
}