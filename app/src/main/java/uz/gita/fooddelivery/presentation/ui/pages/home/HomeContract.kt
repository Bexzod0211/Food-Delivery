package uz.gita.fooddelivery.presentation.ui.pages.home

import org.orbitmvi.orbit.ContainerHost
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.data.model.ProductData

interface HomeContract {
    sealed interface Intent {
        object LoadAllData: Intent
        class ItemClicked(val product: ProductData): Intent
        class SelectOrUnselectCategory(val categoryTitle:String,val select:Boolean): Intent
    }

    sealed interface UiState {
        class InitData(
            val categoryList:List<CategoryData>? = null,
            val categoryTitleList:List<CategoryData>? = null,
            val selectedList: List<String>? = null
        ): UiState
        object Init: UiState
        class Progressbar(val loading:Boolean):UiState
    }

    sealed interface SideEffect {
        class Toast(val message:String): SideEffect
    }

    interface ViewModel:ContainerHost<UiState, SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }

    interface Direction {
        suspend fun openDetailsScreen(product:ProductData)
    }
}