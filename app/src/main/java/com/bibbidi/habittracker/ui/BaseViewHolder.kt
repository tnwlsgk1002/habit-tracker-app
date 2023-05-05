package com.bibbidi.habittracker.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<ITEM, VB : ViewBinding>(binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: ITEM)
}
