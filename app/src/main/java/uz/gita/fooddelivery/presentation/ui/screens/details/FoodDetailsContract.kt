package uz.gita.fooddelivery.presentation.ui.screens.details

import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity

interface FoodDetailsContract {
    sealed interface UiState {
        object Init:UiState
        class IsInCart(val value:Boolean):UiState
    }

    sealed interface Intent {
        class AddToCartClicked(val product:ProductEntity):Intent
        object OpenCartScreenClicked:Intent
        class CheckCart(val product:ProductData):Intent
        object BtnBackClicked:Intent
    }

    sealed interface SideEffect {
        class Toast(val message:String):SideEffect
    }

    interface ViewModel:ContainerHost<UiState,SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun openHomeScreen()
        suspend fun openCartScreen()
        suspend fun back()
    }
}