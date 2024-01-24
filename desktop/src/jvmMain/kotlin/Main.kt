// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.common.App
import com.example.common.root.view.component.RootComponentImpl
import kotlin.system.exitProcess

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()

    val root = runOnUiThread {
        RootComponentImpl(
            componentContext = DefaultComponentContext(lifecycle),
            onExit = { exitProcess(0) }
        )
    }

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Dog breeds"
        ) {
            App(root)
        }
    }
}
