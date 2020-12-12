package am.victor.clean_like_app.utils.extensions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import am.victor.clean_like_app.R
import java.util.*

private const val NOTIFICATION_CHANNEL_NAME = _root_ide_package_.am.victor.clean_like_app.BuildConfig.APPLICATION_ID

fun notify(context: Context, title: String?, body: String?, intent: Intent? = null) =
    createNotificationManager(context)
        .notify(
            ((Date().time / 1000L % Int.MAX_VALUE).toInt()), createNotification(
                context,
                title,
                body,
                intent
            )
        )

private fun createNotificationManager(context: Context): NotificationManager =
    (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                NotificationChannel(
                    context.getString(R.string.default_notification_channel_id),
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
    }

private fun createNotification(
    context: Context,
    title: String?,
    body: String?,
    intent: Intent? = null
): Notification =
    NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
        .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
        .setContentTitle(title)
        .setContentText(body)
        .setStyle(NotificationCompat.BigTextStyle())
        .setVibrate(longArrayOf(1000, 1000))
        .setAutoCancel(true)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).let {
            intent?.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.setContentIntent(
                    PendingIntent.getActivity(
                        context, 0 /* Request code */, this,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                )
            }
            it.build()
        }
