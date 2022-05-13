package com.samvgorode.shiftfourimages.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.samvgorode.shiftfourimages.databinding.ActivityImagesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesListActivity : AppCompatActivity() {

    private var binding: ActivityImagesListBinding? = null

    private val viewModel: ImagesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesListBinding.inflate(layoutInflater).apply {
            viewModel = this@ImagesListActivity.viewModel
            goBack = ::onBackPressed
            setContentView(root)
        }
    }
}