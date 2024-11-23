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

        viewModel.getDailyReminderSetting().observe(viewLifecycleOwner) { isEnabled: Boolean ->
            binding.switchNotification.isChecked = isEnabled
            if (isEnabled) {
                startDailyReminderWorker()
            } else {
                stopDailyReminderWorker()
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isChecked)
        }

        binding.switchNotification.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                viewModel.enableDailyReminder(true)
                startDailyReminderWorker()
                Log.d("SettingsFragment", "Notifikasi diaktifkan")
            } else {
                viewModel.enableDailyReminder(false)
                stopDailyReminderWorker()
            }
        }

        return binding.root
    }

    private fun changeTheme(isDarkMode: Boolean) {
        viewModel.saveThemeSetting(isDarkMode)
    }

    private fun startDailyReminderWorker() {
        val workManager = WorkManager.getInstance(requireContext())
        val workInfos = workManager.getWorkInfosByTag("dailyReminderTag").get()

        val isWorkerRunning = workInfos.any { it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING }

        if (!isWorkerRunning) {
            val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS)
                .addTag("dailyReminderTag")
                .build()
            workManager.enqueue(dailyWorkRequest)
            Log.d("SettingsFragment", "Daily reminder worker scheduled")
        } else {
            Log.d("SettingsFragment", "Worker sudah ada, tidak perlu dijadwalkan ulang")
        }
    }

    private fun stopDailyReminderWorker() {
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag("dailyReminderTag")
        Log.d("SettingsFragment", "Daily reminder dihapus")
    }

}
