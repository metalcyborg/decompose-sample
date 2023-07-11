package com.example.common.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import java.io.InputStream

actual fun convertToImageBitmap(input: InputStream): ImageBitmap {
    return input.buffered().use(::loadImageBitmap)
}