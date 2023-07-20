package uz.gita.fooddelivery.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity


@Dao
interface ProductDao {

    @Insert
    fun addProduct(product:ProductEntity)

    @Delete
    fun deleteProduct(product:ProductEntity)

    @Query("UPDATE products SET count = :count,totalPrice = price*:count WHERE products.id = :id")
    fun updateTotalCount(count:Int,id:Int)

    @Query("SELECT * FROM products")
    fun getAllProductsFromCart():List<ProductEntity>

    @Query("DELETE FROM products")
    fun deleteAllProducts()

    @Query("SELECT count(id) FROM products")
    fun getProductsCount():Flow<Int>
}