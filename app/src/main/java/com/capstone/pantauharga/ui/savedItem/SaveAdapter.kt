package com.capstone.pantauharga.ui.savedItem


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pantauharga.database.PredictInflation
import com.capstone.pantauharga.databinding.ItemSaveeBinding

class SaveAdapter(private val onItemClicked: (PredictInflation) -> Unit) : ListAdapter<PredictInflation, SaveAdapter.SaveViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val binding = ItemSaveeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        val prediction = getItem(position)
        holder.bind(prediction)
    }

    inner class SaveViewHolder(private val binding: ItemSaveeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(prediction: PredictInflation) {
            binding.tvSvComodity.text = prediction.commodityName
            binding.tvSvProvince.text = prediction.provinceName

            binding.root.setOnClickListener {
                onItemClicked(prediction)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PredictInflation>() {
        override fun areItemsTheSame(oldItem: PredictInflation, newItem: PredictInflation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PredictInflation, newItem: PredictInflation): Boolean {
            return oldItem == newItem
        }
    }
}
