package com.example.matchparfait.view.adapters

import android.graphics.Color
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.databinding.ItemColorBinding

class ColorAdapter(
    private val colorList: List<String>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class ColorViewHolder(private val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(colorHex: String) {
            val color = Color.parseColor("#"+colorHex)
            binding.circleView.backgroundTintList = ColorStateList.valueOf(color)

            binding.root.setOnClickListener {
                handleItemClick(adapterPosition)
            }

            if (adapterPosition == selectedPosition) {
                highlightItem(color)
            } else {
                unhighlightItem()
            }
        }

        private fun handleItemClick(position: Int) {
            if (selectedPosition != position) {
                val previousSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(position)
                onItemClick.invoke(position)
            }
        }

        private fun highlightItem(color: Int) {
            binding.root.setBackgroundResource(R.drawable.ic_sparkle)
            binding.circleView.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
            binding.root.backgroundTintList = ColorStateList.valueOf(color)
        }

        private fun unhighlightItem() {
            binding.root.background = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemColorBinding.inflate(inflater, parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val colorItem = colorList[position]
        holder.bind(colorItem)
    }

    override fun getItemCount(): Int {
        return colorList.size
    }
}