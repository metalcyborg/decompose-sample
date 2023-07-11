package com.example.common.root.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.common.details.view.ui.DogDetails
import com.example.common.list.view.ui.DogsList
import com.example.common.root.view.component.RootComponent

@Composable
fun RootUi(component: RootComponent) {
    val stack by component.childStack.subscribeAsState()

    Children(stack) { child ->
        when(val instance = child.instance) {
            is RootComponent.Child.ListChild -> DogsList(instance.component)
            is RootComponent.Child.DetailsChild -> DogDetails(instance.component)
        }
    }
}