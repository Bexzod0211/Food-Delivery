package uz.gita.fooddelivery.presentation.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddelivery.presentation.ui.direction.FoodDetailsDirection
import uz.gita.fooddelivery.presentation.ui.usecase.FoodDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val useCase: FoodDetailsUseCase,
    private val direction: FoodDetailsDirection
) : FoodDetailsContract.ViewModel, ViewModel() {




    override val container = container<FoodDetailsContract.UiState, FoodDetailsContract.SideEffect>(FoodDetailsContract.UiState.Init)

    override fun onEventDispatcher(intent: FoodDetailsContract.Intent) {
        when (intent) {
            is FoodDetailsContract.Intent.AddToCartClicked -> {
                useCase.addProductToCart(intent.product)
                    .onEach { result ->
                        result.onSuccess {
                            intent {
                                postSideEffect(FoodDetailsContract.SideEffect.Toast(it))
                            }
                        }
                    }.launchIn(viewModelScope)
            }

            FoodDetailsContract.Intent.OpenCartScreenClicked -> {
                viewModelScope.launch {
                    direction.openCartScreen()
                }
            }

            is FoodDetailsContract.Intent.CheckCart->{
                useCase.getAllProductsFromCart().onEach {result ->
                    result.onSuccess {list->
                        var isExist = false
                        list.forEach {
                            if (it.title == intent.product.title){
                                isExist = true
                                intent {
                                    reduce {
                                        FoodDetailsContract.UiState.IsInCart(true)
                                    }
                                }
                            }
                        }
                        if (!isExist){
                            intent {
                                reduce {
                                    FoodDetailsContract.UiState.IsInCart(false)
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
            FoodDetailsContract.Intent.BtnBackClicked->{
                viewModelScope.launch {
                    direction.back()
                }
            }
        }
    }
}