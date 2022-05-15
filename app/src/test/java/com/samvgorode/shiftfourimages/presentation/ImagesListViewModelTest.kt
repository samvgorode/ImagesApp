package com.samvgorode.shiftfourimages.presentation

import com.samvgorode.shiftfourimages.domain.getList.GetImagesListUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk


class ImagesListViewModelTest : VmTest() {

    private fun getImagesListUseCase(
        output: List<ImageUiModel> = listOf(),
        error: Throwable? = null
    ): GetImagesListUseCaseImpl {
        val useCase = mockk<GetImagesListUseCaseImpl>()
        if (error == null) coEvery { useCase(1) } returns output
        else coEvery { useCase(1) } throws error
        return useCase
    }
}