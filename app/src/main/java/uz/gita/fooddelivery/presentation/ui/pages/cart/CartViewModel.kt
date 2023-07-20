package uz.gita.fooddelivery.presentation.ui.pages.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddelivery.presentation.ui.usecase.CartUseCase
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase
) : CartContract.ViewModel, ViewModel() {

    override val container = container<CartContract.UiState, CartContract.SideEffect>(CartContract.UiState.Init)

    override fun onEventDispatcher(intent: CartContract.Intent) {
        when (intent) {
            CartContract.Intent.LoadAllData -> {
                useCase.getAllProductsFromCart()
                    .onEach { result ->
                        result.onSuccess {
                            var totalPrice = 0
                            it.forEach {
                                totalPrice += it.totalPrice
                            }
                            intent {
                                reduce {
                                    CartContract.UiState.AllData(it, priceToString(totalPrice))
                                }
                            }
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is CartContract.Intent.UpdateCount -> {
                useCase.updateCountById(intent.count, intent.id)
                    .onEach { result ->
                        result.onSuccess {
                            onEventDispatcher(CartContract.Intent.LoadAllData)
                        }
                    }.launchIn(viewModelScope)
            }
            is CartContract.Intent.DeleteProduct ->{
                useCase.deleteProduct(intent.product)
                    .onEach { result ->
                        result.onSuccess {
                            intent {
                                postSideEffect(CartContract.SideEffect.Toast(it))
                            }
                            onEventDispatcher(CartContract.Intent.LoadAllData)
                        }
                    }.launchIn(viewModelScope)
            }
            CartContract.Intent.DeleteAllProductsInCart ->{
                useCase.deleteAllProductsInCart()
                    .onEach { result ->
                        result.onSuccess {
                            onEventDispatcher(CartContract.Intent.LoadAllData)
                        }
                    }
                    .launchIn(viewModelScope)
            }
        }
    }
}

fun priceToString(price: Int): String {
    var st = StringBuilder("")

    var _price = StringBuilder(price.toString()).reverse().toString()

    for (i in 0 until _price.length step 3) {
        if (i <= _price.length - 3)
            st.append(_price.substring(i, i + 3)).append(" ")
        else st.append(_price.substring(i))
    }

    return st.reverse().toString()
}