package com.capstone.pantauharga.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pantauharga.data.response.DataItem

import com.capstone.pantauharga.databinding.ItemComodityBinding

class KomoditasAdapter (
    private var listKomoditas: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<KomoditasAdapter.EventViewHolder>() {
    inner class EventViewHolder(private val binding: ItemComodityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: DataItem) {
            with(binding) {
                tvComodity.text = event.title
                Glide.with(itemView.context)
                    .load(event.img)
                    .into(image)

                itemView.setOnClickListener {
                    onItemClick(event)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemComodityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(listKomoditas[position])
    }
    override fun getItemCount(): Int = listKomoditas.size

}