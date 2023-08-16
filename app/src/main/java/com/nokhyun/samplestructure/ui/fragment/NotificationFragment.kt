package com.nokhyun.samplestructure.ui.fragment

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.view.View
import androidx.core.app.NotificationCompat
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentNotificationBinding
import com.nokhyun.samplestructure.ui.activity.MainActivity

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    private lateinit var notificationManager: NotificationManager

    override fun init() {
        binding.btnNotification.setOnClickListener { start() }
        notificationManager.cancel(1)
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View {
        notificationManager = requireContext().getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        return view(R.layout.fragment_notification)
    }

    private fun start() {
        val pi = PendingIntent.getActivity(requireContext(), 1001, Intent(requireContext(), MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(requireContext(), "location")
            .setContentTitle("title")
            .setContentText("content")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pi)
            .setNumber(5)
            .setOngoing(true)

        notificationManager.notify(1, notification.build())
    }
}