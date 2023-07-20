package uz.gita.fooddelivery.presentation.ui.screens.sign

import org.orbitmvi.orbit.ContainerHost

interface SignContract {
    sealed interface UiState {
        object Init:UiState
        class Progressbar(val loading:Boolean):UiState
    }

    sealed interface SideEffect {
        class Toast(val message:String):SideEffect
    }

    interface ViewModel : ContainerHost<UiState,SideEffect>{
        fun onEventDispatcher(intent:Intent)
    }

    sealed interface Intent {
        class Login(val email:String,val password:String):Intent
        class SignUp(val firstName:String,val lastName:String,val email: String,val password: String,val confirmPassword:String):Intent
    }

    interface Direction {
        suspend fun openMainScreen()
    }
}