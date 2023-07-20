package uz.gita.fooddelivery.presentation.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.fooddelivery.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AppRepository,
    private val direction: SplashContract.Direction
) : SplashContract.ViewModel, ViewModel() {
    override val container = container<SplashContract.UiState, SplashContract.SideEffect>(SplashContract.UiState.Init)


    init {
        checkUser()
    }

    private fun checkUser() {
        viewModelScope.launch {
            delay(2000)
            repository.hasLoggedIn()
                .onEach {
                    if (it) {
                        direction.openMainScreen()
                    } else direction.openSignScreen()
                }
                .launchIn(viewModelScope)
        }
    }

}