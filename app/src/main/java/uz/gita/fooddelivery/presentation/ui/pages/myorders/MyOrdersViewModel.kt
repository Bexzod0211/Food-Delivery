package uz.gita.fooddelivery.presentation.ui.pages.myorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddelivery.presentation.ui.usecase.MyOrdersUseCase
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val useCase: MyOrdersUseCase
) : MyOrdersContract.ViewModel, ViewModel() {
    override val container = container<MyOrdersContract.UiState, MyOrdersContract.SideEffect>(MyOrdersContract.UiState.Init)
    init {
//        onEventDispatcher(MyOrdersContract.Intent.LoadAllData)
    }

    override fun onEventDispatcher(intent: MyOrdersContract.Intent) {
        when(intent){
            MyOrdersContract.Intent.LoadAllData->{
                intent {
                    reduce {
                        MyOrdersContract.UiState.Progressbar(true)
                    }
                }
                useCase.getMyOrders()
                    .onEach { result ->
                        intent { reduce { MyOrdersContract.UiState.Progressbar(false) } }
                        result.onSuccess {
                            intent {
                                reduce {
                                    MyOrdersContract.UiState.AllData(it)
                                }
                            }
                        }
                        result.onFailure {

                        }
                    }
                    .launchIn(viewModelScope)
            }
        }

    }


}