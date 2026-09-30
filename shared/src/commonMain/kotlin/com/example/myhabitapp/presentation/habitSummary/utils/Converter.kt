package com.example.myhabitapp.presentation.habitSummary.utils

import androidx.compose.ui.graphics.ImageBitmap

expect fun ImageBitmap.bitmapToPng(quality: Int = 100): ByteArray?