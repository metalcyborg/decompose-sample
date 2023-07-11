package com.example.common.details.view.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.subscribe
import com.example.common.details.domain.BreedDetailsInteractor
import com.example.common.list.models.domain.Breed
import com.example.common.details.models.domain.BreedDetails
import com.example.common.utils.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface DogDetailsComponent {

    val breed: Breed
    val breedImage: Value<LoadingState<ImageBitmap>>

    fun onCloseClicked()
}

class DogDetailsComponentImpl(
    componentContext: ComponentContext,
    override val breed: Breed,
    private val interactor: BreedDetailsInteractor,
    private val onFinished: () -> Unit
) : DogDetailsComponent, ComponentContext by componentContext {

    init {
        lifecycle.subscribe(
            onCreate = { loadBreedImage() }
        )
    }

    private val scope = coroutineScope(Dispatchers.Main + SupervisorJob())

    private val _breedImage = MutableValue<LoadingState<ImageBitmap>>(LoadingState.Loading)
    override val breedImage: Value<LoadingState<ImageBitmap>> = _breedImage

    override fun onCloseClicked() {
        onFinished()
    }

    private fun loadBreedImage() {
        scope.launch {
            val image = withContext(Dispatchers.IO) {
                interactor.loadBreedImage(breed)
            }
            _breedImage.value = LoadingState.Loaded(image)
        }
    }
}

sealed class LoadingState<out T> {

    object Loading : LoadingState<Nothing>()

    class Loaded<T>(val result: T) : LoadingState<T>()
}
