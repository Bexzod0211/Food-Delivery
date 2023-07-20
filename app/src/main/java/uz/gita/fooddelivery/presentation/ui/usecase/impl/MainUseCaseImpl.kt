package uz.gita.fooddelivery.presentation.ui.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.usecase.MainUseCase
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) :MainUseCase{
    override fun getBadgesCount(): Flow<Int> {
        return repository.getProductsCountInCart()
    }
}