package uz.gita.fooddelivery.presentation.ui.pages.cart

import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity

interface CartContract {
    sealed interface UiState {
        class AllData(val list:List<ProductEntity>,val totalPrice:String): UiState
        object Init: UiState
        class Progressbar(val isLoading:Boolean):UiState
    }


    sealed interface SideEffect {
        class Toast(val message:String): SideEffect
    }

    sealed interface Intent {
        object LoadAllData: Intent
        class UpdateCount(val count:Int,val id:Int): Intent
        class DeleteProduct(val product:ProductEntity): Intent
        object DeleteAllProductsInCart: Intent
        object SendOrders:Intent
    }

    interface ViewModel:ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {

    }
}