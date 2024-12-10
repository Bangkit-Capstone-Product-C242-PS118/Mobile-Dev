package com.capstone.pantauharga.ui.provinsi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pantauharga.data.response.DataItemDaerah

import com.capstone.pantauharga.databinding.ItemProvBinding


class ProvinceAdapter(
    private val onItemClick: (DataItemDaerah) -> Unit
) : ListAdapter<DataItemDaerah, ProvinceAdapter.ProvinceViewHolder>(DiffCallback) {

    inner class ProvinceViewHolder(private val binding: ItemProvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItemDaerah) {
            binding.tvProvince.text = item.namaDaerah
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
        private val DiffCallback = object : DiffUtil.ItemCallback<DataItemDaerah>() {
            override fun areItemsTheSame(oldItem: DataItemDaerah, newItem: DataItemDaerah): Boolean {
                return oldItem.daerahId == newItem.daerahId
            }

            override fun areContentsTheSame(oldItem: DataItemDaerah, newItem: DataItemDaerah): Boolean {
                return oldItem == newItem
            }
        }
    }
}
