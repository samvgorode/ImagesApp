package com.samvgorode.shiftfourimages.presentation.adapters

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

@BindingAdapter("srsCoil")
fun setSrsCoil(imageView: AppCompatImageView, srsCoil: String?) {
    if (srsCoil.isNullOrBlank().not()) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.load(srsCoil)
    }
}

@BindingAdapter("imagesHistory")
fun setImagesHistory(recyclerView: RecyclerView, imagesHistory: List<ImageUiModel>?) {
    if (imagesHistory.isNullOrEmpty().not()) {
        recyclerView.adapter = ImagesAdapter(imagesHistory!!)
    }
}