package com.capstone.pantauharga.ui.provinsi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pantauharga.data.response.DataItem

import com.capstone.pantauharga.databinding.ItemProvBinding

class ProvinceAdapter(
    private val onItemClick: (DataItem) -> Unit
) : ListAdapter<DataItem, ProvinceAdapter.ProvinceViewHolder>(DiffCallback) {

    inner class ProvinceViewHolder(private val binding: ItemProvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.tvProvince.text = item.title
            Glide.with(binding.root.context)
                .load(item.img)
                .into(binding.imageProv)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val binding = ItemProvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}