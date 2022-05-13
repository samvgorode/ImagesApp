package com.samvgorode.shiftfourimages.presentation.list

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samvgorode.shiftfourimages.domain.GetImagesListUseCase
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesListViewModel @Inject constructor(
    private val getImages: GetImagesListUseCase
) : ViewModel() {

    val imagesList = ObservableField<List<ImageUiModel>>()

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("", "CoroutineExceptionHandler got $exception")
    }

    init {
        viewModelScope.launch(handler) {
            val history = getImages(1)
            imagesList.set(history)
        }
    }
}