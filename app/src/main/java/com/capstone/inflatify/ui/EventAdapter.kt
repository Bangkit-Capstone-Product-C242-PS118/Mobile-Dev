package com.capstone.inflatify.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.capstone.inflatify.data.response.ListEventsItem
import com.capstone.inflatify.databinding.ItemEventsBinding

class EventAdapter (
    private var listEvents: List<ListEventsItem>,
    private val onItemClick: (ListEventsItem) -> Unit
    ) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
        inner class EventViewHolder(private val binding: ItemEventsBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(event: ListEventsItem) {
                with(binding) {
                    tvComodity.text = event.name
                    Glide.with(itemView.context)
                        .load(event.mediaCover)
                        .into(image)

                    itemView.setOnClickListener {
                        onItemClick(event)
                    }

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EventViewHolder(binding)
        }

        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
            holder.bind(listEvents[position])
        }
        override fun getItemCount(): Int = listEvents.size

}