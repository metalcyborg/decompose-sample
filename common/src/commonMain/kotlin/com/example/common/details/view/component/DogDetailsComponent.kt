package com.example.common.details.view.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.subscribe
import com.example.common.details.domain.BreedDetailsInteractor
import com.example.common.list.models.domain.Breed
import com.example.common.mainDispatcher
import com.example.common.utils.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Level
import java.util.logging.Logger

interface DogDetailsComponent {

    val breed: Breed
    val breedImage: Value<LoadingState<ImageBitmap>>

    fun onCloseClicked()

    fun onBackButtonClicked()
}

class DogDetailsComponentImpl(
    componentContext: ComponentContext,
    override val breed: Breed,
    private val interactor: BreedDetailsInteractor,
    private val onFinished: () -> Unit
) : DogDetailsComponent, ComponentContext by componentContext {

    private val logger = Logger.getLogger("Breed list")

    private val scope = coroutineScope(mainDispatcher + SupervisorJob())

    private val _breedImage = MutableValue<LoadingState<ImageBitmap>>(LoadingState.Loading)
    override val breedImage: Value<LoadingState<ImageBitmap>> = _breedImage

    private val loadedImage: ImageKeeper? = instanceKeeper.get("IMAGE") as ImageKeeper?

    init {
        lifecycle.subscribe(
            onCreate = {
                logger.log(Level.INFO, "onCreate")
                loadBreedImage()
            },
            onStart = { logger.log(Level.INFO, "onStart") },
            onResume = { logger.log(Level.INFO, "onResume") },
            onPause = { logger.log(Level.INFO, "onPause") },
            onStop = { logger.log(Level.INFO, "onStop") },
            onDestroy = { logger.log(Level.INFO, "onDestroy") }
        )
    }

    override fun onCloseClicked() {
        onFinished()
    }

    override fun onBackButtonClicked() {
        onFinished()
    }

    private fun loadBreedImage() {
        if (loadedImage != null) {
            val image = loadedImage.imageBitmap
            _breedImage.value = LoadingState.Loaded(image)
        } else {
            scope.launch {
                val image = withContext(Dispatchers.IO) {
                    interactor.loadBreedImage(breed)
                }
                instanceKeeper.put("IMAGE", ImageKeeper(image))
                _breedImage.value = LoadingState.Loaded(image)
            }
        }
    }
}

private data class ImageKeeper(val imageBitmap: ImageBitmap): InstanceKeeper.Instance {
    override fun onDestroy() {

    }
}

sealed class LoadingState<out T> {

    object Loading : LoadingState<Nothing>()

    class Loaded<T>(val result: T) : LoadingState<T>()
}
