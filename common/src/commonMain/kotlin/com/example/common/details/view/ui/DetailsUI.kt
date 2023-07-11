package com.example.common.details.view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.common.details.view.component.DogDetailsComponent
import com.example.common.details.view.component.LoadingState

@Composable
fun DogDetails(component: DogDetailsComponent) {

    val breed = component.breed
    val breedImage = component.breedImage.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = "${breed.name.replaceFirstChar { it.uppercase() }} ${breed.subBreed ?: ""}",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    ) {

        val imageState = breedImage.value
        if (imageState is LoadingState.Loaded) {
            Image(
                bitmap = imageState.result,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}