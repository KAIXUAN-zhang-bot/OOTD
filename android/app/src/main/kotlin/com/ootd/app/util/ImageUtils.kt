package com.ootd.app.util

import android.content.Context
import android.net.Uri
import java.io.File

object ImageUtils {
    fun getImageCacheDir(context: Context): File {
        return File(context.cacheDir, "clothing_images").apply {
            if (!exists()) mkdirs()
        }
    }

    fun getImageFileName(timestamp: Long): String {
        return "clothing_${timestamp}.jpg"
    }

    fun getThumbnailFileName(timestamp: Long): String {
        return "clothing_${timestamp}_thumb.jpg"
    }

    fun compressImagePath(context: Context, timestamp: Long): String {
        return File(getImageCacheDir(context), getImageFileName(timestamp)).absolutePath
    }

    fun getThumbnailPath(context: Context, timestamp: Long): String {
        return File(getImageCacheDir(context), getThumbnailFileName(timestamp)).absolutePath
    }
}
