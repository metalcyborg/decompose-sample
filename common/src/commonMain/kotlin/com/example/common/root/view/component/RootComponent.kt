package com.example.common.root.view.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.lifecycle.subscribe
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
import java.util.logging.Level
import java.util.logging.Logger

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    val dialog: Value<ChildSlot<*, DialogComponent>>

    sealed class Child {

        class ListChild(val component: DogListComponent) : Child()

        class DetailsChild(val component: DogDetailsComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
    onExit: () -> Unit
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    private val backCallback = BackCallback {
        dialogNavigation.activate(DialogConfig("Do you want to close the app?"))
    }

    private val repository = BreedRepositoryImpl()
    private val logger = Logger.getLogger("Root")

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val dialog: Value<ChildSlot<*, DialogComponent>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true
    ) { configuration, componentContext ->
        DialogComponentImpl(
            context = componentContext,
            message = configuration.message,
            onDismissed = dialogNavigation::dismiss,
            onYes = onExit
        )
    }

    init {
        backHandler.register(backCallback)
        lifecycle.subscribe(
            onCreate = { logger.log(Level.INFO, "onCreate") },
            onStart = { logger.log(Level.INFO, "onStart") },
            onResume = { logger.log(Level.INFO, "onResume") },
            onPause = { logger.log(Level.INFO, "onPause") },
            onStop = { logger.log(Level.INFO, "onStop") },
            onDestroy = { logger.log(Level.INFO, "onDestroy") }
        )
        childStack.subscribe {
            updateBackCallback(enabled = it.active.instance is ListChild)
        }
    }

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        // Создание Child-компонентов по конфигурации
        when (config) {
            is Config.List -> ListChild(listComponent(componentContext))
            is Config.Details -> DetailsChild(detailsComponent(componentContext, config))
        }

    private fun listComponent(componentContext: ComponentContext): DogListComponent {
        // Место для передачи зависимостей
        val interactor = BreedInteractor(repository)
        return DogListComponentImpl(
            componentContext = componentContext,
            interactor = interactor,
            onItemSelected = { breed -> navigation.push(Config.Details(breed)) }
        )
    }

    private fun detailsComponent(componentContext: ComponentContext, config: Config.Details): DogDetailsComponent {
        // Место для передачи зависимостей
        // Также берутся данные из конфигурации
        val interactor = BreedDetailsInteractor(repository)
        return DogDetailsComponentImpl(
            componentContext = componentContext,
            breed = config.breed,
            interactor = interactor,
            onFinished = { navigation.pop() }
        )
    }

    private fun updateBackCallback(enabled: Boolean) {
        backCallback.isEnabled = enabled
    }

    /**
     * Обязательно Parcelable
     */
    private sealed class Config : Parcelable {

        @Parcelize
        object List : Config()

        @Parcelize
        data class Details(val breed: Breed) : Config()
    }

    @Parcelize
    private data class DialogConfig(val message: String) : Parcelable
}
