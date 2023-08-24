package com.nokhyun.samplestructure.ui.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.view.View
import androidx.core.app.NotificationCompat
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentNotificationBinding
import com.nokhyun.samplestructure.ui.activity.MainActivity

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    private lateinit var notificationManager: NotificationManager
    private var notifyId = 0

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
        notificationChannel()
        notificationGroup()

        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("title")
            .setContentText("content")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setGroup(requireContext().packageName)
            .setGroupSummary(true)
            .setOngoing(true)

        notificationManager.notify(1234, notification.build())
    }

    private fun notificationGroup() {
        val pi = PendingIntent.getActivity(requireContext(), 1001, Intent(requireContext(), MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE)

        val notificationGroup = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setGroup(requireContext().packageName)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("GroupTitle$notifyId")
            .setContentTitle("GroupContent$notifyId")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle("BigTitle$notifyId")
                    .setBigContentTitle("BigContents$notifyId")
            )
            .setAutoCancel(true)
            .setContentIntent(pi)

        notificationManager.notify(notifyId++, notificationGroup.build())
    }

    private fun notificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        mChannel.description = "descriptionText"
        notificationManager.createNotificationChannel(mChannel)
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_ID"
        private const val CHANNEL_NAME = "NOTIFICATION_NAME"
    }
}