package com.capstone.pantauharga.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.databinding.ItemComodityBinding

class KomoditasAdapter(
    private val onItemClick: (DataItem) -> Unit
) : ListAdapter<DataItem, KomoditasAdapter.KomoditasViewHolder>(KomoditasDiffCallback()) {

    inner class KomoditasViewHolder(private val binding: ItemComodityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(komoditas: DataItem) {
            with(binding) {
                tvComodity.text = komoditas.namaKomoditas
                Glide.with(itemView.context)
                    .load(komoditas.imgUrl)
                    .into(image)

                itemView.setOnClickListener {
                    onItemClick(komoditas)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomoditasViewHolder {
        val binding = ItemComodityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KomoditasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KomoditasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class KomoditasDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.idKomoditas == newItem.idKomoditas
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}
