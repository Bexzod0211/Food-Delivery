package uz.gita.fooddelivery.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.fooddelivery.data.source.local.dao.ProductDao
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import javax.inject.Inject

@Database(entities = [ProductEntity::class], version = 1)
abstract class LocalDatabase: RoomDatabase(){

    abstract fun getProductDao():ProductDao
}