package com.example.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.example.common.App
import com.example.common.root.view.component.RootComponentImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = RootComponentImpl(
            componentContext = defaultComponentContext(),
            onExit = { finish() }
        )

        setContent {
            App(root)
        }
    }
}