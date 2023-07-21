package com.example.common.list.view.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
            TopAppBar(
                title = {
                    Text(
                        text = "Breeds"
                    )
                }
            )
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
            ) {
                items(breeds) {
                    Text(
                        text = "${it.name.replaceFirstChar { firstChar -> firstChar.uppercase() }} ${it.subBreed ?: ""}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                component.onItemClicked(it)
                            }
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    Divider(modifier = Modifier.height(1.dp))
                }
            }
        }
    }
}