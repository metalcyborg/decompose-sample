package com.example.common.common.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.example.common.list.models.domain.Breed

interface BreedRepository {

    suspend fun getBreedList(): List<Breed>

    suspend fun getBreedImageUrls(breed: Breed): List<String>

    suspend fun loadImage(url: String): ImageBitmap
}