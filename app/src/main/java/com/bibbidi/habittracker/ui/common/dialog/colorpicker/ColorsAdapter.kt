package com.bibbidi.habittracker.ui.common.dialog.colorpicker

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bibbidi.habittracker.databinding.ItemPaletteBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.ColorItem

class ColorsAdapter(private val onClick: (ColorItem) -> Unit) :
    ListAdapter<ColorItem, ColorsAdapter.ColorViewHolder>(ColorItemsDiffCallback) {
    class ColorViewHolder(
        private val binding: ItemPaletteBinding,
        private val onClick: (ColorItem) -> Unit
    ) : BaseViewHolder<ColorItem, ItemPaletteBinding>(binding) {

        private var _item: ColorItem? = null

        init {
            binding.containerPalette.setOnClickListener {
                _item?.let(onClick)
            }
        }

        override fun bind(item: ColorItem) {
            _item = item
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(parent.viewBinding(ItemPaletteBinding::inflate), onClick)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ColorItemsDiffCallback : DiffUtil.ItemCallback<ColorItem>() {
        override fun areItemsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
            return oldItem.colorUiModel.id == newItem.colorUiModel.id
        }

        override fun areContentsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
            return oldItem == newItem
        }
    }
}
