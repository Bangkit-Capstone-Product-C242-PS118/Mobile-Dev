package com.capstone.pantauharga.ui.savedItem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pantauharga.data.retrofit.ApiConfig
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.PredictInflation
import com.capstone.pantauharga.databinding.FragmentSavedItemBinding
import com.capstone.pantauharga.repository.PredictInflationRepository
import com.capstone.pantauharga.ui.detail.DetailActivity
import com.capstone.pantauharga.ui.detail.DetailViewModel

class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSavedItemBinding
    private lateinit var viewModel: SaveViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val apiService = ApiConfig.getApiService()
        val repository = PredictInflationRepository(apiService, database)
        val factory = SaveViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory)[SaveViewModel::class.java]

        val adapter = SaveAdapter { prediction ->
            navigateToDetailActivity(prediction)
        }

        binding.rvSave.layoutManager = LinearLayoutManager(context)
        binding.rvSave.adapter = adapter

        viewModel.allPredictions.observe(viewLifecycleOwner) { predictions ->
            adapter.submitList(predictions)
        }
    }

    private fun navigateToDetailActivity(prediction: PredictInflation) {
        val intent = Intent(requireContext(), SavePredictActivity::class.java).apply {
            putExtra("prediction", prediction)
        }
        startActivity(intent)
    }

}
