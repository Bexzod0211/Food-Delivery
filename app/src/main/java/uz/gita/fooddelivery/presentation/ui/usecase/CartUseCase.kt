package uz.gita.fooddelivery.presentation.ui.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity

interface CartUseCase {
    fun getAllProductsFromCart():Flow<Result<List<ProductEntity>>>
    fun updateCountById(count:Int,id:Int):Flow<Result<Unit>>
    fun deleteProduct(product:ProductEntity):Flow<Result<String>>
    fun deleteAllProductsInCart():Flow<Result<Unit>>
    fun sendOrders():Flow<Result<String>>
}