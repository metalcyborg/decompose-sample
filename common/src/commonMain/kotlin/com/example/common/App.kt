package com.example.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.common.root.view.component.RootComponent
import com.example.common.root.view.ui.RootUi

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        Surface {
            RootUi(root)
        }
    }
}
