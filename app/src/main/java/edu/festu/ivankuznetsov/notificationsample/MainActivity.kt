package edu.festu.ivankuznetsov.notificationsample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import edu.festu.ivankuznetsov.notificationsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val channel = NotificationChannel(
        CHANNEL_ID, "Sample channel",
        NotificationManager.IMPORTANCE_HIGH
    )

    private var i = 1

    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        channel.description = getString(R.string.channel_desc)
        channel.enableVibration(false)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(this, 0,
                Intent(this,MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            showNotification(pendingIntent)
        }
    }

    private fun getNotification(pendingIntent: PendingIntent):Notification =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("This is sample app")
            .setContentText("This is content text")
            .setSubText("sub_text")
            .setContentIntent(pendingIntent)
            .setSettingsText("set_text")
            .setSmallIcon(R.drawable.ic_notify_small)
            .setLargeIcon(AppCompatResources
                .getDrawable(this,R.drawable.ic_notify_large)
                ?.toBitmap(48,48))
            .setAutoCancel(true).build()

    private fun showNotification(pendingIntent: PendingIntent){
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
        Log.d(TAG, "SHOWING NOTIFICATION")
        manager.notify(i++,getNotification(pendingIntent))
    }
    companion object{
        val TAG = MainActivity::class.java.simpleName
        val CHANNEL_ID = "Sample Notification"
    }
}