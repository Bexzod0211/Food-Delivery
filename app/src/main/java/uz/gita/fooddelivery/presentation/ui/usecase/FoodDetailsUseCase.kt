package uz.gita.fooddelivery.presentation.ui.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity

interface FoodDetailsUseCase {
    fun addProductToCart(product:ProductEntity):Flow<Result<String>>
    fun getAllProductsFromCart():Flow<Result<List<ProductEntity>>>
}