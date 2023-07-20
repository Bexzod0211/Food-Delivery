package uz.gita.fooddelivery.presentation.ui.screens.sign

import android.widget.ProgressBar
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
import uz.gita.fooddelivery.presentation.ui.usecase.SignUseCase
import uz.gita.fooddelivery.utils.myLog
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val useCase: SignUseCase,
    private val direction: SignContract.Direction
):SignContract.ViewModel,ViewModel(){

    override fun onEventDispatcher(intent: SignContract.Intent) {
        when(intent){
            is SignContract.Intent.Login->{

                if (intent.email.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Email is empty"))
                    }
                    return
                }
                if (!intent.email.contains("@") || intent.email.trim().length<=9){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Enter a valid email address"))
                    }
                    return
                }

                if (intent.password.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Password is empty"))
                    }
                    return
                }

                if (intent.password.trim().length <6){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Password length must contain at least 6 letters"))
                    }
                    return
                }
                intent {
                    reduce {
                        SignContract.UiState.Progressbar(true)
                    }
                }

                useCase.login(intent.email.trim(),intent.password.trim())
                    .onEach { result ->
                    intent {
                        reduce {
                            SignContract.UiState.Progressbar(false)
                        }
                    }
                        result.onSuccess {
                            intent {
                                postSideEffect(SignContract.SideEffect.Toast(it))
                            }
                            direction.openMainScreen()
                        }
                        result.onFailure {
                            intent {
                                postSideEffect(SignContract.SideEffect.Toast(it.message?:""))
                            }
                            myLog(it.message?:"")
                        }

                    }
                    .launchIn(viewModelScope)
            }
            is SignContract.Intent.SignUp->{
                if (intent.firstName.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Firstname is empty"))
                    }
                    return
                }

                if (intent.lastName.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Lastname is empty"))
                    }
                    return
                }

                if (intent.email.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Email is empty"))
                    }
                    return
                }
                if (!intent.email.contains("@") || intent.email.trim().length<=9){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Enter a valid email address"))
                    }
                    return
                }

                if (intent.password.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Password is empty"))
                    }
                    return
                }

                if (intent.password.trim().length <6){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Password length must contain at least 6 letters"))
                    }
                    return
                }

                if (intent.confirmPassword.trim().isEmpty()){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Confirm password is empty"))
                    }
                    return
                }

                if (intent.password != intent.confirmPassword){
                    intent {
                        postSideEffect(SignContract.SideEffect.Toast("Passwords do not match"))
                    }
                    return
                }
                intent {
                    reduce {
                        SignContract.UiState.Progressbar(true)
                    }
                }
                useCase.signUp(intent.firstName,intent.lastName,intent.email,intent.password)
                    .onEach {result ->
                        intent {
                            reduce {
                                SignContract.UiState.Progressbar(false)
                            }
                        }
                        result.onSuccess {
                            intent {
                                postSideEffect(SignContract.SideEffect.Toast(it))
                            }
                            direction.openMainScreen()
                        }
                        result.onFailure {
                            intent {
                                postSideEffect(SignContract.SideEffect.Toast(it.message?:""))
                            }
                            myLog(it.message?:"")
                        }

                    }
                    .launchIn(viewModelScope)
            }
        }
    }

    override val container = container<SignContract.UiState, SignContract.SideEffect>(SignContract.UiState.Init)
}