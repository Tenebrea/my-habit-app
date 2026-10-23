package com.example.myhabitapp.presentation.habitSummary.utils

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class AndroidShare(private val context: Context) : PlatformShare {
    override fun shareImage(bytes: ByteArray, title: String) {
        val cacheFolder = File(context.cacheDir, "shared_images").apply { mkdirs() }
        val imageFile = File(cacheFolder, "habit_summary_${System.currentTimeMillis()}.png")

        FileOutputStream(imageFile).use { it.write(bytes) }

        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_SUBJECT, title)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(intent, title).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooserIntent)
    }

}

@Composable
actual fun rememberPlatformShare(): PlatformShare {
    val context = LocalContext.current.applicationContext
    return remember(context) { AndroidShare(context) }
}