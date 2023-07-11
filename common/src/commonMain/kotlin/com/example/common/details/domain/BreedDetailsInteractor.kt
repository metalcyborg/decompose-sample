package com.example.common.details.domain

import androidx.compose.ui.graphics.ImageBitmap
import com.example.common.common.domain.BreedRepository
import com.example.common.details.models.domain.BreedDetails
import com.example.common.list.models.domain.Breed
import kotlin.random.Random

class BreedDetailsInteractor(
    private val repository: BreedRepository
) {

    suspend fun loadBreedDetails(breed: Breed): BreedDetails {
        val imageUrls = repository.getBreedImageUrls(breed)
        val index = Random.nextInt(until = imageUrls.size)
        return BreedDetails(imageUrl = imageUrls[index])
    }

    suspend fun loadBreedImage(breed: Breed): ImageBitmap {
        val imageUrls = repository.getBreedImageUrls(breed)
        val index = Random.nextInt(until = imageUrls.size)
        val url = imageUrls[index]
        return repository.loadImage(url)
    }
}
