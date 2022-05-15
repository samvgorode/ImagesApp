package com.samvgorode.shiftfourimages.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samvgorode.shiftfourimages.databinding.ImageWidgetBinding
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

class ImagesAdapter : ListAdapter<ImageUiModel, HistoryViewHolder>(callback) {

    var favoriteClick: ((String, Boolean) -> Unit)? = null
    var imageClick: ((ImageUiModel) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        ImageWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::HistoryViewHolder).also {
                it.setListeners(favoriteClick, imageClick)
            }


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        currentList.getOrNull(position)?.let(holder::bind)
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int) =
        currentList.getOrNull(position)?.hashCode()?.toLong() ?: -1L

    companion object {
        val callback = object : DiffUtil.ItemCallback<ImageUiModel>() {
            override fun areItemsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean =
                oldItem.url == newItem.url &&
                        oldItem.favorite == newItem.favorite

        }
    }
}

class HistoryViewHolder(private val binding: ImageWidgetBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(imageModel: ImageUiModel) {
        binding.imageModel = imageModel
    }
    fun setListeners(favoriteClick: ((String, Boolean) -> Unit)?, imageClick: ((ImageUiModel) -> Unit)?) {
        binding.rootClick = imageClick
        binding.favoriteClick = favoriteClick
    }
}