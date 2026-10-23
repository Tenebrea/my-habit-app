package com.example.myhabitapp.presentation.habitSummary.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FileOutputStream

class DesktopShare() : PlatformShare {
    override fun shareImage(bytes: ByteArray, title: String) {
        val fileDialog = FileDialog(null as Frame?, title, FileDialog.SAVE).apply {
            file = "habit_summary.png"
            isVisible = true
        }

        val directory = fileDialog.directory
        val fileName = fileDialog.file
        if (directory != null && fileName != null) {
            val file = File(directory, fileName)
            FileOutputStream(file).use { it.write(bytes) }
        }
    }
}

@Composable
actual fun rememberPlatformShare(): PlatformShare = remember { DesktopShare() }