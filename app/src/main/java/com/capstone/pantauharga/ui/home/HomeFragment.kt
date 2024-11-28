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
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.databinding.FragmentHomeBinding
import com.capstone.pantauharga.ui.KomoditasAdapter
import com.capstone.pantauharga.ui.provinsi.ProvinsiActivity
import com.capstone.pantauharga.ui.provinsi.ProvinsiActivity.Companion.EVENT_ID_KEY
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        (activity as AppCompatActivity).supportActionBar?.hide()

        setupRecyclerViews()

        viewModel.komoditas.observe(viewLifecycleOwner) { komoditas -> setKomoditas(komoditas) }

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

    private fun setKomoditas(komoditas: List<DataItem>) {
        val komoditasAdapter = KomoditasAdapter(komoditas) { event ->
            val intent = Intent(activity, ProvinsiActivity::class.java).apply {
                putExtra(EVENT_ID_KEY, event.id.toString())
            }
            startActivity(intent)
        }
        binding.recyclerViewFinished.adapter = komoditasAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}