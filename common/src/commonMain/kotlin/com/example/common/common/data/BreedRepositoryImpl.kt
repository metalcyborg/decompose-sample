package com.example.common.common.data

import androidx.compose.ui.graphics.ImageBitmap
import com.example.common.common.domain.BreedRepository
import com.example.common.common.models.data.BreedImages
import com.example.common.common.models.data.BreedList
import com.example.common.list.models.domain.Breed
import com.example.common.utils.convertToImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.jackson.*
import io.ktor.utils.io.jvm.javaio.*

class BreedRepositoryImpl : BreedRepository {

    private val client = HttpClient {
        install(ContentNegotiation) {
            jackson()
        }
    }

    override suspend fun getBreedList(): List<Breed> {
        val breedList: BreedList = client.get("https://dog.ceo/api/breeds/list/all").body()
        return breedList.message.flatMap { (name, subBreedList) ->
            if (subBreedList.isEmpty()) {
                listOf(Breed(name))
            } else {
                subBreedList.map { Breed(name, it) }
            }
        }
    }

    override suspend fun getBreedImageUrls(breed: Breed): List<String> {
        val images: BreedImages = client.get("https://dog.ceo/api/breed/${breed.name}/images").body()
        val breedAndSubBreed = if (breed.subBreed == null) breed.name else "${breed.name}-${breed.subBreed}"
        return images.message.filter { it.contains("/${breedAndSubBreed}/") }
    }

    override suspend fun loadImage(url: String): ImageBitmap {
        return client.prepareGet(url).execute { response ->
            response.bodyAsChannel().toInputStream().use(::convertToImageBitmap)
        }
    }
}