package com.samvgorode.shiftfourimages.presentation.image

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import com.samvgorode.shiftfourimages.databinding.ActivityImageBinding
import com.samvgorode.shiftfourimages.presentation.ext.saveImageInQ
import com.samvgorode.shiftfourimages.presentation.ext.saveTheImageLegacy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ImageActivity : AppCompatActivity() {

    private var binding: ActivityImageBinding? = null

    private val viewModel: ImageViewModel by viewModels()

    private val uiState = ObservableField<ImageUiState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater).apply {
            imageUiState = this@ImageActivity.uiState
            goBack = ::onBackPressed
            onShare = ::onShareImage
            favoriteClick = ::onFavoriteClick
            setContentView(root)
        }
    }

    override fun onStart() {
        super.onStart()
        observeViewModel()
        getSelectedImage()
    }

    private fun onShareImage() {
        binding?.includedImage?.image?.let { imageView ->
            imageView.invalidate()
            val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap
            if (bitmap != null) {
                lifecycleScope.launch {
                    withContext(Dispatchers.Default) {
                        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) saveImageInQ(
                            bitmap,
                            applicationContext.contentResolver
                        )
                        else saveTheImageLegacy(bitmap)
                        if (uri != null) shareImageUri(uri)
                    }
                }
            }
        }
    }

    private fun shareImageUri(uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_STREAM, uri)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .setType("image/png")
        startActivity(intent)
    }

    private fun getSelectedImage() {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImageIntent.GetSelectedImage)
        }
    }

    private fun onFavoriteClick(id: String, favorite: Boolean) {
        lifecycleScope.launch {
            viewModel.userIntent.send(ImageIntent.SetImageFavorite(id, favorite))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch { viewModel.state.collect(uiState::set) }
    }

    companion object {
        private const val IMAGE_EXTRA =
            "com.samvgorode.shiftfourimages.presentation.image.IMAGE_EXTRA"

        fun getIntent(context: AppCompatActivity) =
            Intent(context, ImageActivity::class.java)
    }
}