package com.capstone.inflatify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.inflatify.R
import com.capstone.inflatify.data.response.ListEventsItem
import com.capstone.inflatify.databinding.FragmentHomeBinding
import com.capstone.inflatify.ui.EventAdapter
import com.capstone.inflatify.ui.detail.DetailEventActivity
import com.capstone.inflatify.ui.detail.DetailEventActivity.Companion.EVENT_ID_KEY
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        (activity as AppCompatActivity).supportActionBar?.hide()

        setupRecyclerViews()

        viewModel.finishedEvents.observe(viewLifecycleOwner) { events -> setEventDataFinished(events) }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, "Failed to load Data", Toast.LENGTH_SHORT).show()
                viewModel.setError(false)
            }
        }

        val searchBar = binding.searchBar

        val shapeModel = ShapeAppearanceModel.builder()
            .setAllCornerSizes(30f)
            .build()

        @Suppress("DEPRECATION") val materialShapeDrawable = MaterialShapeDrawable(shapeModel).apply {
            setStroke(3f, resources.getColor(R.color.blue))
        }

        searchBar.background = materialShapeDrawable


        return binding.root

    }

    private fun setupRecyclerViews() {
        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(context)
    }

    private fun setEventDataFinished(events: List<ListEventsItem>) {
        eventAdapter = EventAdapter(events) { selectedEvent ->
            val intent = Intent(context, DetailEventActivity::class.java).apply {
                putExtra(EVENT_ID_KEY, selectedEvent.id.toString())
            }
            startActivity(intent)
        }
        binding.recyclerViewFinished.adapter = eventAdapter
        eventAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}