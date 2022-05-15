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
        domainList: List<ImageEntity> = listOf(),
        uiList: List<ImageUiModel> = listOf(),
    ) = mockk<ImagesDomainMapper> {
        domainList.forEachIndexed { index, image ->
            every { mapToUi(image) } returns uiList.get(index)
        }
        uiList.forEachIndexed { index, image ->
            every { mapToDomain(image) } returns domainList.get(index)

        }
    }
}