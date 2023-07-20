package uz.gita.fooddelivery.presentation.ui.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.usecase.FoodDetailsUseCase
import javax.inject.Inject

class FoodDetailsUseCaseImpl @Inject constructor(
    private val repository: AppRepository
):FoodDetailsUseCase{

    override fun addProductToCart(product: ProductEntity): Flow<Result<String>> {
        return repository.addProductToCart(product)
    }

    override fun getAllProductsFromCart(): Flow<Result<List<ProductEntity>>> {
        return repository.getAllProductsFromCart()
    }

}