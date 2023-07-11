package com.example.common.utils

import androidx.compose.ui.graphics.ImageBitmap
import java.io.InputStream

expect fun convertToImageBitmap(input: InputStream): ImageBitmap