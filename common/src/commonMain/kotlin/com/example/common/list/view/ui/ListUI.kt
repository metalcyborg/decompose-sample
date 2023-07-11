package com.example.common.list.view.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.common.list.view.component.DogListComponent

@Composable
fun DogsList(component: DogListComponent) {

    val breeds by component.breedList.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = "Breeds",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    ) {
        if (breeds.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(breeds) {
                    Text(
                        text = "${it.name.replaceFirstChar { firstChar -> firstChar.uppercase() }} ${it.subBreed ?: ""}",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                component.onItemClicked(it)
                            }
                    )
                    Divider(modifier = Modifier.height(1.dp))
                }
            }
        }
    }
}