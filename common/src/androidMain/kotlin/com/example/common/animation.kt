package com.example.common

import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide

actual val stackAnimator: StackAnimator = slide()