package uz.gita.fooddelivery.presentation.ui.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.model.MyOrderData

interface MyOrdersUseCase {
    fun getMyOrders():Flow<Result<List<MyOrderData>>>
}