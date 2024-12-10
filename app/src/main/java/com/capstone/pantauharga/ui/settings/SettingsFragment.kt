package com.capstone.pantauharga.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.pantauharga.databinding.FragmentSettingsBinding
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by activityViewModels { SettingViewModelFactory(SettingPreferences.getInstance(requireContext().dataStore)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkMode ->
            binding.switchTheme.isChecked = isDarkMode
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isChecked)
        }


        return binding.root
    }

    private fun changeTheme(isDarkMode: Boolean) {
        viewModel.saveThemeSetting(isDarkMode)
    }



}
