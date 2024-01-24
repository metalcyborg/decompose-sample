package com.example.common.root.view.component

import com.arkivanov.decompose.ComponentContext

interface DialogComponent {

    val message: String

    fun onDismissClicked()

    fun onYesClicked()
}

class DialogComponentImpl(
    private val context: ComponentContext,
    override val message: String,
    private val onDismissed: () -> Unit,
    private val onYes: () -> Unit
): DialogComponent, ComponentContext by context {

    override fun onDismissClicked() {
        onDismissed()
    }

    override fun onYesClicked() {
        onYes()
    }
}

