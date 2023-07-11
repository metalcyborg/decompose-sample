package com.example.common.list.view.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.subscribe
import com.example.common.list.domain.BreedInteractor
import com.example.common.list.models.domain.Breed
import com.example.common.utils.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val scope = coroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        lifecycle.subscribe(
            onCreate = { loadItems() }
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
