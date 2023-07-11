package com.example.common.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.InputStream

actual fun convertToImageBitmap(input: InputStream): ImageBitmap {
    return input.buffered().use(BitmapFactory::decodeStream).asImageBitmap()
}