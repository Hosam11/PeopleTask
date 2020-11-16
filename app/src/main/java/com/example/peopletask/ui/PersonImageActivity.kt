package com.example.peopletask.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.peopletask.R
import com.example.peopletask.databinding.ActivityPersonImageBinding
import com.example.peopletask.domain.PersonImage
import com.example.peopletask.util.Util
import timber.log.Timber
import java.io.File

class PersonImageActivity : AppCompatActivity() {
    private var downloadId: Int = 0

    // Notification ID.
    private val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityPersonImageBinding>(
            this, R.layout.activity_person_image
        )
        binding.lifecycleOwner = this

        // get the passed personImage from the intent and assign it to data binding variable to
        // display it's content
        val selectImageIntent = intent
        val personImage = selectImageIntent.getParcelableExtra<PersonImage>(Util.PERSON_IMAGE_KEY)
        binding.personImage = personImage

        // display image in right size
        if (personImage != null) {
            binding.imageId.layoutParams.width = personImage.width
            binding.imageId.layoutParams.height = personImage.height
        }

        // build the image url with size and pathName of image then pass it to download() function
        val imageUrl = applicationContext.getString(
            R.string.image_url,
            applicationContext.getString(R.string.img_size_original),
            personImage?.imgPath
        )
        // get file from image url to be name of image
        val fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1)
        // get a path folder to save the images in it
        val dirPath = this.getExternalFilesDir(null)?.absolutePath + File.separator + "downloads"
        // create channel for notification with id and name
        createChannel(Util.CHANNEL_ID, Util.CHANNEL_NAME)
        binding.downloadImageBtn.setOnClickListener {
            Timber.i("dirPath = $dirPath")
            Timber.i("fileName = $fileName")
            if (Util.isNetworkAvailable(this)) {
                downloadImage(imageUrl, dirPath, fileName)
            } else {
                Util.showAlert(this)
            }
        }


    }

    private fun downloadImage(
        imageUrl: String,
        dirPath: String,
        fileName: String
    ) {
        // get NotificationBuilder
        val builder: NotificationCompat.Builder = notificationBuilder()

        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())

        downloadId = PRDownloader.download(imageUrl, dirPath, fileName)
            .build()
            .setOnProgressListener { progress ->
                // Timber.i("onProgress >> currentBytes= ${progress?.currentBytes} ## totalBytes= ${progress.totalBytes} ")
                builder.setProgress(
                    progress.totalBytes.toInt(),
                    progress.currentBytes.toInt(),
                    false
                )
                // update notification progress
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Timber.i("down comp")
                    builder.setProgress(0, 0, false)
                    builder.setContentText(getString(R.string.down_complete))
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())

                }

                override fun onError(error: Error?) {
                    Timber.i("onError: ${error.toString()}  ${error?.connectionException.toString()} ")
                    builder.setProgress(0, 0, false)
                    builder.setContentText(getString(R.string.error_download_notification))
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
                }

            })

        val status = PRDownloader.getStatus(downloadId)
        Timber.i("status ${status.name}")
    }

    private fun notificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            applicationContext,
            /*channel id*/Util.CHANNEL_ID
        ) // set title, text and icon to builder
            .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(applicationContext.getString(R.string.notification_body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            with(notificationChannel) {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Downloading"
            }
            // request an instance of the NotificationManager from the system.
            val notificationManager = this.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

}