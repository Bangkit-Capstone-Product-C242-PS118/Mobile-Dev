package com.capstone.pantauharga.ui.home

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
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.ListEventsItem
import com.capstone.pantauharga.databinding.FragmentHomeBinding
import com.capstone.pantauharga.ui.MainAdapter
import com.capstone.pantauharga.ui.detail.DetailEventActivity
import com.capstone.pantauharga.ui.detail.DetailEventActivity.Companion.EVENT_ID_KEY
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainAdapter: MainAdapter

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
        mainAdapter = MainAdapter(events) { selectedEvent ->
            val intent = Intent(context, DetailEventActivity::class.java).apply {
                putExtra(EVENT_ID_KEY, selectedEvent.id.toString())
            }
            startActivity(intent)
        }
        binding.recyclerViewFinished.adapter = mainAdapter
        mainAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}