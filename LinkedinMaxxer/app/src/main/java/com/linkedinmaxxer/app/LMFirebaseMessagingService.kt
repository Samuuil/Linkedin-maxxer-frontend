package com.linkedinmaxxer.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LMFirebaseMessagingService : FirebaseMessagingService() {

    private val accountRepository: AccountRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (SessionManager.getToken().isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                accountRepository.updatePushToken(token)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title ?: message.data["title"] ?: return
        val body = message.notification?.body ?: message.data["body"] ?: return
        val suggestionId = message.data["suggestionId"]
        showNotification(title, body, suggestionId)
    }

    private fun showNotification(title: String, body: String, suggestionId: String?) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Comment Suggestions",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply { description = "New comment suggestions from tracked LinkedIn profiles" }
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            suggestionId?.let { putExtra(EXTRA_SUGGESTION_ID, it) }
        }
        val pendingIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {
        const val CHANNEL_ID = "lm_comment_suggestions"
        const val EXTRA_SUGGESTION_ID = "suggestionId"
    }
}
