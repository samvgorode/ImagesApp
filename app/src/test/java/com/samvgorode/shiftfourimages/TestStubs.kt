package com.samvgorode.shiftfourimages

import com.samvgorode.shiftfourimages.data.local.ImageEntity
import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk

object TestStubs {

    fun getImagesRepository(
        input: String = "",
        output: ImageEntity = mockk(),
        output1: List<ImageEntity> = listOf()
    ) = mockk<ImagesRepository> {
        coEvery { getImages(1) } returns output1
    }

    fun getImagesRepositoryError(
        input: String = "",
        output: Throwable = mockk()
    ) = mockk<ImagesRepository> {
        coEvery { getImages(1) } throws output
    }

    fun getDomainMapper(
        input: List<ImageEntity> = listOf(),
        output: List<ImageUiModel> = listOf(),
    ) = mockk<ImagesDomainMapper> {
        input.forEachIndexed { index, image ->
            every { map(image) } returns output.get(index)
        }
    }
}