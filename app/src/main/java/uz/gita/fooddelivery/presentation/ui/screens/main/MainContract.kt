package uz.gita.fooddelivery.presentation.ui.screens.main

import org.orbitmvi.orbit.ContainerHost

interface MainContract {
    interface ViewModel : ContainerHost<UiState,SideEffect>{

    }

    sealed interface UiState {
        object Init:UiState
        class BadgeCount(val count:Int):UiState
    }

    sealed interface SideEffect {

    }


}