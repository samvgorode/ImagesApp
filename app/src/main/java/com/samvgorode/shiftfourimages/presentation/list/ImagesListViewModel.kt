package com.samvgorode.shiftfourimages.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samvgorode.shiftfourimages.domain.favorite.GetAllImagesFavoriteUseCase
import com.samvgorode.shiftfourimages.domain.favorite.GetImageFavoriteUseCase
import com.samvgorode.shiftfourimages.domain.favorite.SetImageFavoriteUseCase
import com.samvgorode.shiftfourimages.domain.getList.GetImagesListUseCase
import com.samvgorode.shiftfourimages.domain.lastSelected.GetSelectedImageUseCase
import com.samvgorode.shiftfourimages.domain.lastSelected.SetSelectedImageUseCase
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
class ImagesListViewModel @Inject constructor(
    private val getImages: GetImagesListUseCase,
    private val setImageFavorite: SetImageFavoriteUseCase,
    private val getImageFavorite: GetImageFavoriteUseCase,
    private val setSelectedImage: SetSelectedImageUseCase,
    private val getSelectedImage: GetSelectedImageUseCase,
    private val getAllFavoriteImages: GetAllImagesFavoriteUseCase
) : ViewModel() {

    val userIntent = Channel<ImagesListIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(ImagesListUiState())
    val state: StateFlow<ImagesListUiState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is ImagesListIntent.GetImagesList -> getImagesList(intent.page)
                    is ImagesListIntent.Refresh -> refresh()
                    is ImagesListIntent.SetImageFavorite -> setFavorite(intent.id, intent.favorite)
                    is ImagesListIntent.SetImageSelected -> setSelectedImage(intent.image)
                    is ImagesListIntent.RefreshLastSelectedImage -> refreshSelectedImage()
                    is ImagesListIntent.ShowJustFavorites -> showJustFavorites()
                }
            }
        }
    }

    // set favorite flag in SP and Put model to Room and update UI
    private fun setFavorite(id: String, favorite: Boolean) {
        val image = _state.value.images.find { it.id == id }
        val imageToSave = image?.copy(favorite = favorite)
        if (imageToSave != null) {
            viewModelScope.launch { setImageFavorite(imageToSave) }
            _state.update {
                try {
                    setOrSwitchFavorite(id)
                } catch (e: Throwable) {
                    getErrorState()
                }
            }
        }
    }

    // do refresh from 1 page
    private fun refresh() {
        viewModelScope.launch {
            _state.update { getLoadingState() }
            _state.update {
                try {
                    val images = getImages(1)
                    _state.value.copy(
                        isLoading = false,
                        isError = false,
                        images = images,
                        showJustFavorites = false
                    )
                } catch (e: Throwable) {
                    getErrorState()
                }
            }
        }
    }

    // get 10 images for requested page
    private fun getImagesList(page: Int) {
        viewModelScope.launch {
            _state.update { getLoadingState() }
            _state.update {
                try {
                    val images = getImages(page)
                    val currentImages = _state.value.images
                    if (images.none { currentImages.contains(it) }) _state.value.copy(
                        isLoading = false,
                        isError = false,
                        images = currentImages + images
                    )
                    else _state.value.copy(isLoading = false, isError = false)
                } catch (e: Throwable) {
                    getErrorState()
                }
            }
        }
    }

    private fun refreshSelectedImage() {
        viewModelScope.launch {
            getSelectedImage()?.id?.let { lastSelectedId ->
                val isFavorite = getImageFavorite(lastSelectedId)
                _state.update { setOrSwitchFavorite(lastSelectedId, isFavorite) }
            }
        }
    }

    private fun setOrSwitchFavorite(id: String, favorite: Boolean? = null): ImagesListUiState {
        val images = mutableListOf<ImageUiModel>()
        _state.value.images.forEach {
            images.add(
                if (it.id == id) it.copy(favorite = favorite ?: it.favorite.not())
                else it
            )
        }

        return _state.value.copy(
            isLoading = false,
            isError = false,
            images = if(_state.value.showJustFavorites) images.filter { it.favorite } else images
        )
    }

    private fun showJustFavorites() {
        viewModelScope.launch {
            _state.update { getLoadingState() }
            val images = getAllFavoriteImages()
            _state.update {
                _state.value.copy(isLoading = false, isError = false, images = images, showJustFavorites = true)
            }
        }
    }

    private fun getLoadingState() = _state.value.copy(isLoading = true)

    private fun getErrorState() =
        _state.value.copy(isLoading = false, isError = true, images = listOf())
}