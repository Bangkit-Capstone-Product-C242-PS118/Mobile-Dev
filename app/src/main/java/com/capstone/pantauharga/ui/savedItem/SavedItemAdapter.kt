package com.capstone.pantauharga.ui.savedItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pantauharga.database.FavoriteEvents
import com.capstone.pantauharga.databinding.ItemSaveBinding

class SavedItemAdapter(private val onItemClick: (FavoriteEvents) -> Unit, private val onUnfavoriteClick: (FavoriteEvents) -> Unit) : RecyclerView.Adapter<SavedItemAdapter.FavoriteViewHolder>() {

    private var favoriteEvents: List<FavoriteEvents> = listOf()

    inner class FavoriteViewHolder(private val binding: ItemSaveBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvents) {
            binding.komoditas.text = event.name
            Glide.with(binding.imgSave.context)
                .load(event.mediaCover)
                .into(binding.imgSave)

            itemView.setOnClickListener {
                onItemClick(event)
            }

            binding.root.setOnLongClickListener {
                onUnfavoriteClick(event)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemSaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteEvents[position])
    }

    override fun getItemCount(): Int = favoriteEvents.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<FavoriteEvents>) {
        favoriteEvents = list
        notifyDataSetChanged()
    }
}
