package com.example.myhabitapp.presentation.habitSummary.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

actual fun ImageBitmap.bitmapToPng(quality: Int): ByteArray? {
    ByteArrayOutputStream().use { bytes ->
        this.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, quality, bytes)
        return bytes.toByteArray()
    }
}