package uz.gita.fooddelivery.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val imageUrl:String,
    val price:Int,
    val totalPrice:Int,
    val info:String,
    val count:Int
)
