package com.capstone.pantauharga.ui.savedItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.databinding.ItemSaveeBinding

class HargaKomoditasAdapter(
    private val onItemClicked: (HargaKomoditas) -> Unit
) : ListAdapter<HargaKomoditas, HargaKomoditasAdapter.HargaKomoditasViewHolder>(HargaKomoditasDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HargaKomoditasViewHolder {
        val binding = ItemSaveeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HargaKomoditasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HargaKomoditasViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class HargaKomoditasViewHolder(private val binding: ItemSaveeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HargaKomoditas, onClick: (HargaKomoditas) -> Unit) {
            binding.tvSvComodity.text = data.commodityName
            binding.tvSvProvince.text = data.provinceName
            binding.root.setOnClickListener { onClick(data) }
        }
    }
}

class HargaKomoditasDiffCallback : DiffUtil.ItemCallback<HargaKomoditas>() {
    override fun areItemsTheSame(oldItem: HargaKomoditas, newItem: HargaKomoditas): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HargaKomoditas, newItem: HargaKomoditas): Boolean {
        return oldItem == newItem
    }
}
