package com.example.common.list.view.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.subscribe
import com.example.common.list.domain.BreedInteractor
import com.example.common.list.models.domain.Breed
import com.example.common.mainDispatcher
import com.example.common.utils.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Level
import java.util.logging.Logger

interface DogListComponent {

    val breedList: Value<List<Breed>>

    fun onItemClicked(breed: Breed)
}

class DogListComponentImpl(
    componentContext: ComponentContext,
    private val interactor: BreedInteractor,
    private val onItemSelected: (breed: Breed) -> Unit
) : DogListComponent, ComponentContext by componentContext {

    private val _breedList = MutableValue<List<Breed>>(emptyList())
    override val breedList: Value<List<Breed>> = _breedList

    private val scope = coroutineScope(mainDispatcher + SupervisorJob())
    private val logger = Logger.getLogger("Breed list")

    init {
        lifecycle.subscribe(
            onCreate = {
                logger.log(Level.INFO, "onCreate")
                loadItems()
            },
            onStart = { logger.log(Level.INFO, "onStart") },
            onResume = { logger.log(Level.INFO, "onResume") },
            onPause = { logger.log(Level.INFO, "onPause") },
            onStop = { logger.log(Level.INFO, "onStop") },
            onDestroy = { logger.log(Level.INFO, "onDestroy") }
        )
    }

    private fun loadItems() {
        scope.launch {
            val breeds = withContext(Dispatchers.IO) {
                interactor.getBreeds()
            }
            _breedList.value = breeds
        }
    }

    override fun onItemClicked(breed: Breed) {
        onItemSelected(breed)
    }
}
