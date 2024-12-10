package com.capstone.pantauharga.ui.savedItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.databinding.ItemNormalPricesBinding

class NormalPricesAdapter(
    private val onItemClicked: (NormalPrice) -> Unit
) : ListAdapter<NormalPrice, NormalPricesAdapter.NormalPricesViewHolder>(NormalPricesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalPricesViewHolder {
        val binding = ItemNormalPricesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NormalPricesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NormalPricesViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class NormalPricesViewHolder(private val binding: ItemNormalPricesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NormalPrice, onClick: (NormalPrice) -> Unit) {
            binding.tvSvComodity.text = data.commodityName
            binding.tvSvProvince.text = data.provinceName
            binding.root.setOnClickListener { onClick(data) }
        }
    }
}

class NormalPricesDiffCallback : DiffUtil.ItemCallback<NormalPrice>() {
    override fun areItemsTheSame(oldItem: NormalPrice, newItem: NormalPrice): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NormalPrice, newItem: NormalPrice): Boolean {
        return oldItem == newItem
    }
}
