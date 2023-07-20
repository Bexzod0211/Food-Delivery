package uz.gita.fooddelivery.presentation.ui.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.usecase.SignUseCase
import javax.inject.Inject

class SignUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) :SignUseCase{
    override fun login(email: String, password: String): Flow<Result<String>> {
        return repository.login(email, password)
    }

    override fun signUp(firstName: String, lastName: String, email: String, password: String): Flow<Result<String>> {
        return repository.createAccount(email, password)
    }
}