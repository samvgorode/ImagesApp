package com.samvgorode.shiftfourimages.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samvgorode.shiftfourimages.databinding.ImageWidgetBinding
import com.samvgorode.shiftfourimages.presentation.ImageUiModel

class ImagesAdapter(val list: List<ImageUiModel>) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        ImageWidgetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::HistoryViewHolder)


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        list.getOrNull(position)?.let(holder::bind)
    }

    override fun getItemCount(): Int = list.size

}

class HistoryViewHolder(private val binding: ImageWidgetBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(imageModel: ImageUiModel) {
        binding.imageModel = imageModel
    }
}