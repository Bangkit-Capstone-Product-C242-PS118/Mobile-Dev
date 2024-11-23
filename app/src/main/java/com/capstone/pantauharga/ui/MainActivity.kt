package com.capstone.pantauharga.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.pantauharga.R
import com.capstone.pantauharga.databinding.ActivityMainBinding
import com.capstone.pantauharga.ui.home.HomeViewModel
import com.capstone.pantauharga.ui.settings.SettingPreferences
import com.capstone.pantauharga.ui.settings.SettingsViewModel
import com.capstone.pantauharga.ui.settings.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingPreferences = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = SettingsViewModel(settingPreferences)
        settingsViewModel.getThemeSettings().observe(this) { isDarkMode ->
            applyTheme(isDarkMode)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_save, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }





    private fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}