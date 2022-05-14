package com.samvgorode.shiftfourimages.presentation.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
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

@BindingAdapter("srsDrawable")
fun setSrsCoil(imageView: AppCompatImageView, srsDrawable: Drawable?) {
    if (srsDrawable != null) imageView.setImageDrawable(srsDrawable)
}

@BindingAdapter("images")
fun setImagesHistory(recyclerView: RecyclerView, images: List<ImageUiModel>?) {
    if (images.isNullOrEmpty().not()) {
        if (recyclerView.adapter == null) recyclerView.adapter = ImagesAdapter()
        (recyclerView.adapter as? ImagesAdapter)?.submitList(images!!)
    }
}

@BindingAdapter("favoriteClick", "imageClick")
fun setListeners(recyclerView: RecyclerView, favoriteClick: (String, Boolean) -> Unit, imageClick: (String) -> Unit) {
    if (recyclerView.adapter == null) recyclerView.adapter = ImagesAdapter()
    (recyclerView.adapter as? ImagesAdapter)?.let {
        it.favoriteClick = favoriteClick
        it.imageClick = imageClick
    }
}

@BindingAdapter("onLoadMoreCallback")
fun setOnLoadMoreCallback(recyclerView: RecyclerView, onLoadMoreCallback: (page: Int) -> Unit) {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val position = (recyclerView.layoutManager as? LinearLayoutManager)
                ?.findLastCompletelyVisibleItemPosition()
                ?: RecyclerView.NO_POSITION
            if (position != RecyclerView.NO_POSITION) {
                val positionToCalculateFrom = position + 1
                if (positionToCalculateFrom >= 10 && positionToCalculateFrom % 10 == 0)
                    onLoadMoreCallback.invoke(positionToCalculateFrom / 10 + 1)
            }
        }
    })
}