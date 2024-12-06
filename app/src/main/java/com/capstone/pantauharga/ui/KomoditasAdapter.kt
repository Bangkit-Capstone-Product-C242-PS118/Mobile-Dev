package com.capstone.pantauharga.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pantauharga.data.response.ListCommoditiesItem
import com.capstone.pantauharga.databinding.ItemComodityBinding

class KomoditasAdapter(
    private val onItemClick: (ListCommoditiesItem) -> Unit
) : ListAdapter<ListCommoditiesItem, KomoditasAdapter.KomoditasViewHolder>(KomoditasDiffCallback()) {

    inner class KomoditasViewHolder(private val binding: ItemComodityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(komoditas: ListCommoditiesItem) {
            with(binding) {
                tvComodity.text = komoditas.title
                Glide.with(itemView.context)
                    .load(komoditas.img)
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

    class KomoditasDiffCallback : DiffUtil.ItemCallback<ListCommoditiesItem>() {
        override fun areItemsTheSame(oldItem: ListCommoditiesItem, newItem: ListCommoditiesItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListCommoditiesItem, newItem: ListCommoditiesItem): Boolean {
            return oldItem == newItem
        }
    }
}
