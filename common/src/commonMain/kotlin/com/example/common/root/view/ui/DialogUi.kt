package com.example.common.root.view.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.root.view.component.DialogComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogUi(
    component: DialogComponent
) {
    AlertDialog(
        onDismissRequest = { component.onDismissClicked() },
        text = {
            Text(component.message)
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { component.onYesClicked() }
                ) {
                    Text("Yes")
                }
                TextButton(
                    onClick = { component.onDismissClicked() }
                ) {
                    Text("No")
                }
            }
        }
    )
}
