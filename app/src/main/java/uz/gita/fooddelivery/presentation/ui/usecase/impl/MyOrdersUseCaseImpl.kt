package uz.gita.fooddelivery.presentation.ui.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.model.MyOrderData
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.usecase.MyOrdersUseCase
import javax.inject.Inject

class MyOrdersUseCaseImpl @Inject constructor(
    private val repository: AppRepository
):MyOrdersUseCase{
    override fun getMyOrders(): Flow<Result<List<MyOrderData>>> {
        return repository.getMyOrders()
    }
}