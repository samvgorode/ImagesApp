package com.samvgorode.shiftfourimages.presentation.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samvgorode.shiftfourimages.domain.favorite.SetImageFavoriteUseCase
import com.samvgorode.shiftfourimages.domain.lastSelected.GetSelectedImageUseCase
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val setImageFavorite: SetImageFavoriteUseCase,
    private val getSelectedImageUseCase: GetSelectedImageUseCase
) : ViewModel() {

    val userIntent = Channel<ImageIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(ImageUiState())
    val state: StateFlow<ImageUiState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is ImageIntent.SetImageFavorite -> setFavorite(intent.favorite)
                    is ImageIntent.GetSelectedImage -> getSelectedImage()
                }
            }
        }
    }

    private fun getSelectedImage() {
        val image = getSelectedImageUseCase()
        _state.update {
            if (image != null) _state.value.copy(
                isLoading = false,
                isError = false,
                image = image
            )
            else getErrorState()
        }
    }

    // set favorite flag in SP and Put model to Room and update UI
    private fun setFavorite(favorite: Boolean) {
        val image = _state.value.image
        val imageToSave = image.copy(favorite = favorite)
        viewModelScope.launch { setImageFavorite(imageToSave) }
        _state.update {
            try {
                _state.value.copy(
                    isLoading = false,
                    isError = false,
                    image = imageToSave
                )
            } catch (e: Throwable) {
                getErrorState()
            }
        }
    }

    private fun getErrorState() =
        _state.value.copy(isLoading = false, isError = true, image = ImageUiModel())

}