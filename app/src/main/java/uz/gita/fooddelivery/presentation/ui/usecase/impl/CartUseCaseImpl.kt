package uz.gita.fooddelivery.presentation.ui.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.usecase.CartUseCase
import javax.inject.Inject

class CartUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) :CartUseCase{
    override fun getAllProductsFromCart(): Flow<Result<List<ProductEntity>>> {
        return repository.getAllProductsFromCart()
    }

    override fun updateCountById(count: Int, id: Int): Flow<Result<Unit>> {
        return repository.updateTotalCountAndPriceById(count,id)
    }

    override fun deleteProduct(product: ProductEntity): Flow<Result<String>> {
        return repository.deleteProductFromCart(product)
    }

    override fun deleteAllProductsInCart(): Flow<Result<Unit>> {
        return repository.deleteAllProductsInCart()
    }

    override fun sendOrders(): Flow<Result<String>> {
        return repository.sendOrders()
    }
}