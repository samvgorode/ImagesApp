package com.samvgorode.shiftfourimages.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samvgorode.shiftfourimages.domain.GetImagesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val getImages: GetImagesListUseCase
) : ViewModel() {

    val userIntent = Channel<ImagesListIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(ImagesListUiState())
    val state: StateFlow<ImagesListUiState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is ImagesListIntent.GetImagesList -> getImagesList(it.page)
                }
            }
        }
    }

    private fun getImagesList(page: Int) {
        viewModelScope.launch {
            _state.update { _state.value.copy(isLoading = true) }
            _state.update {
                try {
                    val images = getImages(page)
                    _state.value.copy(isLoading = false, isError = false, images = images)
                } catch (e: Throwable) {
                    _state.value.copy(isLoading = false, isError = true, images = listOf())
                }
            }
        }
    }
}