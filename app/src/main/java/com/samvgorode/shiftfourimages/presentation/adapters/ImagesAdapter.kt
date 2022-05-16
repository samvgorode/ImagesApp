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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        ImageWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::HistoryViewHolder).also {
                it.setListeners(favoriteClick, imageClick)
            }


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else (payloads.firstOrNull() as? ImageUiModel)?.let(holder::bind)
    }

    override fun getItemCount(): Int = currentList.size

    companion object {
        val callback = object : DiffUtil.ItemCallback<ImageUiModel>() {
            override fun areItemsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageUiModel, newItem: ImageUiModel): Boolean =
                oldItem == newItem

            override fun getChangePayload(
                oldItem: ImageUiModel,
                newItem: ImageUiModel
            ): ImageUiModel? =
                if (oldItem.favorite != newItem.favorite) newItem else null
        }
    }
}

class HistoryViewHolder(private val binding: ImageWidgetBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(imageModel: ImageUiModel) {
        binding.imageModel = imageModel
    }

    fun setListeners(
        favoriteClick: ((String, Boolean) -> Unit)?,
        imageClick: ((ImageUiModel) -> Unit)?
    ) {
        binding.rootClick = imageClick
        binding.favoriteClick = favoriteClick
    }
}