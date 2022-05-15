package com.samvgorode.shiftfourimages.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import com.samvgorode.shiftfourimages.R
import com.samvgorode.shiftfourimages.databinding.ActivityImagesListBinding
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import com.samvgorode.shiftfourimages.presentation.ext.startAnotherActivity
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
            switchList = ::switchList
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
        val switchBtnText = binding?.switchBtn?.text
        if(switchBtnText == getString(R.string.show_favorites)) refresh()
        else showFavorites()
    }

    private fun onImageClick(imageModel: ImageUiModel) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.SetImageSelected(imageModel))
        }
        startAnotherActivity<ImageActivity>()
    }

    private fun onFavoriteClick(id: String, favorite: Boolean) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.SetImageFavorite(id, favorite))
        }
    }

    private fun switchList(btnText: String) {
        if(btnText == getString(R.string.show_favorites)) onRefresh()
        else showFavorites()
    }

    private fun observeViewModel() = lifecycleScope.launch { viewModel.state.collect(uiState::set) }


    private fun getImages(page: Int) = lifecycleScope.launch {
        viewModel.userIntent.send(ImagesListIntent.GetImagesList(page))
    }

    private fun showFavorites() {
        lifecycleScope.launch { viewModel.userIntent.send(ImagesListIntent.ShowJustFavorites) }
    }

    private fun refresh() {
        lifecycleScope.launch { viewModel.userIntent.send(ImagesListIntent.Refresh) }
    }
}