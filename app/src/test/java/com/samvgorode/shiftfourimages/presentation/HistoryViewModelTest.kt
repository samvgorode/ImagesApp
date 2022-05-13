package com.samvgorode.shiftfourimages.presentation

import com.samvgorode.shiftfourimages.domain.GetImagesListUseCase
import com.samvgorode.shiftfourimages.presentation.list.ImagesListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class HistoryViewModelTest : VmTest() {

    @Test
    fun `vm should create request on init and get response`() = runBlocking {
        val output: List<ImageUiModel> = listOf()
        val useCase = getImagesListUseCase(output)
        val vm = ImagesListViewModel(useCase)
        coVerify { useCase.invoke(1) }
        assertEquals(vm.imagesList.get(), output)
    }

    @Test
    fun `vm should create request on init and throw error`() = runBlocking {
        val output: Throwable = mockk()
        val useCase = getImagesListUseCase(error = output)
        try {
            ImagesListViewModel(useCase)
        } catch (e: Throwable) {
            assertEquals(e, output)
        }
        coVerify { useCase.invoke(1) }
    }

    private fun getImagesListUseCase(
        output: List<ImageUiModel> = listOf(),
        error: Throwable? = null
    ): GetImagesListUseCase {
        val useCase = mockk<GetImagesListUseCase>()
        if (error == null) coEvery { useCase(1) } returns output
        else coEvery { useCase(1) } throws error
        return useCase
    }
}