package uz.gita.fooddelivery.presentation.ui.pages.myorders

import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddelivery.data.model.MyOrderData

interface MyOrdersContract {

    sealed interface UiState {
        object Init:UiState
        class AllData(val myOrders:List<MyOrderData>):UiState
        class Progressbar(val isLoadIng:Boolean):UiState

    }

    sealed interface SideEffect {
        class Toast(val message:String):SideEffect
    }

    sealed interface Intent {
        object LoadAllData:Intent
    }

    interface ViewModel : ContainerHost<UiState,SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {

    }

}