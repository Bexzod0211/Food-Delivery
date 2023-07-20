package uz.gita.fooddelivery.presentation.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddelivery.presentation.ui.usecase.MainUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase
) : MainContract.ViewModel, ViewModel(){
    override val container =  container<MainContract.UiState, MainContract.SideEffect>(MainContract.UiState.Init)

    init {
        useCase.getBadgesCount()
            .onEach {
                intent {
                    reduce {
                        MainContract.UiState.BadgeCount(it)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}