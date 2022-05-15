package com.samvgorode.shiftfourimages.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import com.samvgorode.shiftfourimages.databinding.ActivityImagesListBinding
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import com.samvgorode.shiftfourimages.presentation.image.ImageActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesListActivity : AppCompatActivity() {

    private var binding: ActivityImagesListBinding? = null

    private val viewModel: ImagesListViewModel by viewModels()

    private val uiState = ObservableField<ImagesListUiState>()
    private var lastLoadedPage = 1
    private val onLoadMoreCallback: (Int) -> Unit = {
        if (it > lastLoadedPage) {
            getImages(it)
            lastLoadedPage = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesListBinding.inflate(layoutInflater).apply {
            uiState = this@ImagesListActivity.uiState
            goBack = ::onBackPressed
            loadMore = onLoadMoreCallback
            refresh = ::onRefresh
            imageClick = ::onImageClick
            favoriteClick = ::onFavoriteClick
            setContentView(root)
        }
    }

    override fun onStart() {
        super.onStart()
        observeViewModel()
        getImages(lastLoadedPage)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.RefreshLastSelectedImage)
        }
    }

    private fun onRefresh() {
        lastLoadedPage = 1
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.Refresh)
        }
    }

    private fun onImageClick(imageModel: ImageUiModel) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.SetImageSelected(imageModel))
        }
        startActivity(ImageActivity.getIntent(this))
    }

    private fun onFavoriteClick(id: String, favorite: Boolean) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.SetImageFavorite(id, favorite))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch { viewModel.state.collect(uiState::set) }
    }

    private fun getImages(page: Int) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.GetImagesList(page))
        }
    }
}