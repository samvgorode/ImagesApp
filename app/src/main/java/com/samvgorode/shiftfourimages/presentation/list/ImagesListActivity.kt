package com.samvgorode.shiftfourimages.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import com.samvgorode.shiftfourimages.databinding.ActivityImagesListBinding
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesListActivity : AppCompatActivity() {

    private var binding: ActivityImagesListBinding? = null

    private val viewModel: ImagesListViewModel by viewModels()

    private val imagesList = ObservableField<List<ImageUiModel>>()
    private val isLoading = ObservableBoolean()
    private val isError = ObservableBoolean()
    private val isStub = ObservableBoolean()
    private var lastLoadedPage = 0
    private val onLoadMoreCallback: (Int) -> Unit = {
        if (it > lastLoadedPage) {
            sendIntent(it)
            lastLoadedPage = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesListBinding.inflate(layoutInflater).apply {
            imagesList = this@ImagesListActivity.imagesList
            isLoading = this@ImagesListActivity.isLoading
            isError = this@ImagesListActivity.isError
            isStub = this@ImagesListActivity.isStub
            goBack = ::onBackPressed
            loadMore = onLoadMoreCallback
            setContentView(root)
        }
    }

    override fun onStart() {
        super.onStart()
        observeViewModel()
        sendIntent(1)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                isLoading.set(it.isLoading)
                isError.set(it.isError)
                isStub.set(it.images.isEmpty())
                imagesList.set(it.images)
            }
        }
    }

    private fun sendIntent(page: Int) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImagesListIntent.GetImagesList(page))
        }
    }
}