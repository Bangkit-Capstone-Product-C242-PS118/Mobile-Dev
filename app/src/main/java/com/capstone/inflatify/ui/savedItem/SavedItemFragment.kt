package com.capstone.inflatify.ui.savedItem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.inflatify.database.FavoriteEvents
import com.capstone.inflatify.databinding.FragmentSavedItemBinding
import com.capstone.inflatify.ui.detail.DetailEventActivity
import com.capstone.inflatify.ui.detail.DetailEventActivity.Companion.EVENT_ID_KEY


class SavedItemFragment : Fragment() {
    private lateinit var binding: FragmentSavedItemBinding
    private lateinit var viewModel: SavedItemViewModel
    private lateinit var adapter: SavedItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedItemBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SavedItemViewModel::class.java]

        setupRecyclerView()

        viewModel.getFavoriteEvents()

        viewModel.favoriteEvents.observe(viewLifecycleOwner) { events -> setEventData(events) }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, "Failed to load Data", Toast.LENGTH_SHORT).show()
                viewModel.setError(false)
            }
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = SavedItemAdapter(
            onItemClick = { selectedEvent ->
                val intent = Intent(context, DetailEventActivity::class.java).apply {
                    putExtra(EVENT_ID_KEY, selectedEvent.id)
                }
                startActivity(intent)
            },
            onUnfavoriteClick = { event ->
                viewModel.removeFavoriteEvent(event)
            }
        )
        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.adapter = adapter
    }

    private fun setEventData(events: List<FavoriteEvents>) {
        adapter.submitList(events)
    }
}
