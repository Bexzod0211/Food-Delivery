package uz.gita.fooddelivery.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity

interface AppRepository {
    fun getAllData():Flow<Result<List<CategoryData>>>
    fun addProductToCart(product:ProductEntity):Flow<Result<String>>
    fun deleteProductFromCart(product: ProductEntity):Flow<Result<String>>
    fun updateTotalCountAndPriceById(count:Int,id:Int):Flow<Result<Unit>>
    fun getAllProductsFromCart():Flow<Result<List<ProductEntity>>>
    fun deleteAllProductsInCart():Flow<Result<Unit>>
    fun selectOrUnselectCategory(categoryTitle: String,select:Boolean):Flow<Result<List<CategoryData>>>
    fun getAllCategoriesTitle():Flow<Result<List<CategoryData>>>
    val selectedList:List<String>
    fun getProductsCountInCart():Flow<Int>
    fun createAccount(email:String,password:String):Flow<Result<String>>
    fun login(email:String,password: String):Flow<Result<String>>
    fun hasLoggedIn():Flow<Boolean>
}