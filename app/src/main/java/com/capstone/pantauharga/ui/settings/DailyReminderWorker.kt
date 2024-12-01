package com.capstone.pantauharga.ui.settings

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.retrofit.ApiConfig

class DailyReminderWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("DailyReminderWorker", "Worker berjalan")
        val apiService = ApiConfig.getApiService()
        val response = apiService.getEvents(active = -1, limit = 1)

        if (response.listEvents.isNotEmpty()) {
            val nearestEvent = response.listEvents[0]
            showNotification(nearestEvent.name, nearestEvent.beginTime, nearestEvent.id)
        }

        return Result.success()
    }


    private fun showNotification(eventName: String, eventTime: String, eventId: Int) {
        Log.d("DailyReminderWorker", "Menampilkan notifikasi")
        val notificationManager = NotificationManagerCompat.from(context)

        val channelId = "daily_reminder_channel"
        val channelName = "Daily Reminder Channel"
        val channelDescription = "Channel for daily reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val notificationManagerService = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
        notificationManagerService.createNotificationChannel(channel)

        val detailIntent = Intent(context, InflationPredictActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            detailIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Daily Reminder")
            .setContentText("$eventName at $eventTime")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1, notification)
        } else {
            Log.e("DailyReminderWorker", "Izin notifikasi tidak diberikan")
        }
    }
}