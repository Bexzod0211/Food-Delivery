package uz.gita.fooddelivery.presentation.ui.screens.splash

import org.orbitmvi.orbit.ContainerHost

interface SplashContract {
    interface ViewModel : ContainerHost<UiState,SideEffect>{

    }

    sealed interface UiState {
        object Init:UiState
    }

    sealed class SideEffect {

    }

    interface Direction {
        suspend fun openMainScreen()
        suspend fun openSignScreen()
    }
}