package uz.gita.fooddelivery.presentation.ui.pages.home

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
import uz.gita.fooddelivery.presentation.ui.usecase.HomeUseCase
import uz.gita.fooddelivery.utils.myLog
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
    private val direction: HomeContract.Direction
) : HomeContract.ViewModel, ViewModel() {

    override val container = container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState.Init)

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when(intent){
            HomeContract.Intent.LoadAllData ->{
                intent {
                    reduce {
                        HomeContract.UiState.Progressbar(true)
                    }
                    useCase.getALlData().onEach {result ->
                        intent {
                            reduce {
                                HomeContract.UiState.Progressbar(false)
                            }
                        }
                        result.onSuccess { list->
                            useCase.getAllCategoriesTitle().onEach {result->
                                result.onSuccess {categoryTitleList->
                                    intent {
                                        reduce {
                                            myLog("$categoryTitleList")
                                            HomeContract.UiState.InitData(list, categoryTitleList, useCase.selectedList)
                                        }
                                    }
                                }
                                result.onFailure {
                                    myLog(it.message?:"")
                                    postSideEffect(HomeContract.SideEffect.Toast(it.message ?: ""))
                                }
                            }.launchIn(viewModelScope)
                        }
                        result.onFailure {
                            myLog(it.message?:"")
                            postSideEffect(HomeContract.SideEffect.Toast(it.message ?: ""))
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is HomeContract.Intent.ItemClicked ->{
                viewModelScope.launch {
                    direction.openDetailsScreen(intent.product)
                }
            }
            is HomeContract.Intent.SelectOrUnselectCategory ->{
                useCase.selectOrUnselectCategory(intent.categoryTitle,intent.select)
                    .onEach {result ->
                        result.onSuccess {list->
                            useCase.getAllCategoriesTitle().onEach {result->
                                result.onSuccess {categoryTitleList->
                                    intent {
                                        reduce {
                                            myLog("$categoryTitleList")
                                            HomeContract.UiState.InitData(list, categoryTitleList, useCase.selectedList)
                                        }
                                    }
                                }
                                result.onFailure {
                                    myLog(it.message?:"")
                                    intent {
                                        postSideEffect(HomeContract.SideEffect.Toast(it.message ?: ""))
                                    }
                                }
                            }.launchIn(viewModelScope)
                        }
                        result.onFailure {
                            myLog(it.message?:"")
                            intent {
                                postSideEffect(HomeContract.SideEffect.Toast(it.message ?: ""))
                            }

                        }
                    }
                    .launchIn(viewModelScope)
            }

        }
    }

}
