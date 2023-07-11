package com.example.common.root.view.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.common.details.view.component.DogDetailsComponent
import com.example.common.details.view.component.DogDetailsComponentImpl
import com.example.common.common.data.BreedRepositoryImpl
import com.example.common.details.domain.BreedDetailsInteractor
import com.example.common.list.domain.BreedInteractor
import com.example.common.list.models.domain.Breed
import com.example.common.list.view.component.DogListComponent
import com.example.common.list.view.component.DogListComponentImpl
import com.example.common.root.view.component.RootComponent.Child.DetailsChild
import com.example.common.root.view.component.RootComponent.Child.ListChild

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {

        class ListChild(val component: DogListComponent) : Child()

        class DetailsChild(val component: DogDetailsComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val repository = BreedRepositoryImpl()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.List -> ListChild(listComponent(componentContext))
            is Config.Details -> DetailsChild(detailsComponent(componentContext, config))
        }

    private fun listComponent(componentContext: ComponentContext): DogListComponent {
        val interactor = BreedInteractor(repository)
        return DogListComponentImpl(
            componentContext = componentContext,
            interactor = interactor,
            onItemSelected = { breed -> navigation.push(Config.Details(breed)) }
        )
    }

    private fun detailsComponent(componentContext: ComponentContext, config: Config.Details): DogDetailsComponent {
        val interactor = BreedDetailsInteractor(repository)
        return DogDetailsComponentImpl(
            componentContext = componentContext,
            breed = config.breed,
            interactor = interactor
        ) {
            navigation.pop()
        }
    }

    private sealed class Config : Parcelable {

        @Parcelize
        object List : Config()

        @Parcelize
        data class Details(val breed: Breed) : Config()
    }
}
