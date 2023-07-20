package uz.gita.fooddelivery.domain.repository

import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.data.source.local.LocalDatabase
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import uz.gita.fooddelivery.utils.myLog
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val localDb: LocalDatabase,
    private val auth:FirebaseAuth
) : AppRepository {

    private val dao = localDb.getProductDao()
    override val selectedList = mutableListOf<String>()
    override fun getProductsCountInCart(): Flow<Int> {
        return dao.getProductsCount()
    }

    override fun createAccount(email: String, password: String): Flow<Result<String>> = callbackFlow{
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(Result.success("You have signed up successfully"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun login(email: String, password: String): Flow<Result<String>> = callbackFlow{
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                trySend(Result.success("You have logged in successfully"))
            }
            .addOnFailureListener {
                myLog(it.message?:"")
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun hasLoggedIn(): Flow<Boolean> = flow{
        emit(auth.currentUser != null)
    }

    private suspend fun getAllCategory() = withContext(Dispatchers.IO) {
        try {
            val list = arrayListOf<CategoryData>()
            val snapshot = fireStore.collection("categories").get().await()
            for (doc in snapshot) {
                val subListSnapshot = doc.reference.collection("items").get().await()

                val subList = arrayListOf<ProductData>()



                for (item in subListSnapshot) {
//                    myLog("item id = ${(item.get("id") as Long).toInt()} title = ${item.get("title")} imageUrl = ${item.get("imageUrl")} price = ${item.get("price")} info = ${item.get("info")}")
                    subList.add(
                        ProductData(
                            id = (item.get("id") as Long).toInt(),
                            title = item.get("title") as String,
                            imageUrl = item.get("imageUrl") as String,
                            price = item.get("price") as String,
                            info = item.get("info") as String
                        )
                    )
                }

                list.add(
                    CategoryData(
                        id = (doc.get("id") as Long).toInt(),
                        title = doc.get("title").toString(),
                        items = subList
                    )
                )
            }
            Result.success(list)
        } catch (e: Exception) {
//            myLog(e.message?:"")
            Result.failure(e)
        }

    }

    override fun getAllData(): Flow<Result<List<CategoryData>>> = flow {
        emit(getAllCategory())
    }

    override fun addProductToCart(product: ProductEntity): Flow<Result<String>> = flow {
        dao.addProduct(product)
        emit(Result.success("${product.title} has been added to cart"))
    }

    override fun deleteProductFromCart(product: ProductEntity): Flow<Result<String>> = flow {
        dao.deleteProduct(product)
        emit(Result.success("${product.title} has been removed from cart"))
    }

    override fun updateTotalCountAndPriceById(count: Int, id: Int): Flow<Result<Unit>> = flow {
        dao.updateTotalCount(count, id)
        emit(Result.success(Unit))
    }

    override fun getAllProductsFromCart(): Flow<Result<List<ProductEntity>>> = flow {
        emit(Result.success(dao.getAllProductsFromCart()))
    }

    override fun deleteAllProductsInCart(): Flow<Result<Unit>> = flow {
        dao.deleteAllProducts()
        emit(Result.success(Unit))
    }

    private suspend fun getAllSelectedCategoryData(): Result<List<CategoryData>> = withContext(Dispatchers.IO) {
        val list = mutableListOf<CategoryData>()
        if (selectedList.isNotEmpty()) {
            selectedList.forEach {
                getCategoryDataByTitle(it)?.let { categoryData ->
                    list.add(categoryData)
                }
            }

            return@withContext Result.success(list)
        } else return@withContext getAllCategory()
    }

    override fun selectOrUnselectCategory(categoryTitle: String, select: Boolean): Flow<Result<List<CategoryData>>> = callbackFlow {
        if (select)
            selectedList.add(categoryTitle)
        else selectedList.remove(categoryTitle)
        trySend(getAllSelectedCategoryData())
        awaitClose()
    }


    override fun getAllCategoriesTitle(): Flow<Result<List<CategoryData>>> = callbackFlow {
        val list = mutableListOf<CategoryData>()
        fireStore.collection("categories")
            .get()
            .addOnSuccessListener { documents ->
                documents.forEach {
                    list.add(
                        CategoryData(
                            id = (it.get("id") as Long).toInt(),
                            title = it.get("title").toString(),
                            items = listOf()
                        )
                    )
                }
                trySend(Result.success(list))

            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    private suspend fun getCategoryDataByTitle(title: String): CategoryData? {

        var data: CategoryData? = null

        try {


            val documents = fireStore.collection("categories").whereEqualTo("title", title).get().await()


            for (doc in documents) {
                val subListSnapshot = doc.reference.collection("items").get().await()

                val subList = arrayListOf<ProductData>()



                for (item in subListSnapshot) {
//                    myLog("item id = ${(item.get("id") as Long).toInt()} title = ${item.get("title")} imageUrl = ${item.get("imageUrl")} price = ${item.get("price")} info = ${item.get("info")}")
                    subList.add(
                        ProductData(
                            id = (item.get("id") as Long).toInt(),
                            title = item.get("title") as String,
                            imageUrl = item.get("imageUrl") as String,
                            price = item.get("price") as String,
                            info = item.get("info") as String
                        )
                    )
                }


                data = CategoryData(
                    id = (doc.get("id") as Long).toInt(),
                    title = doc.get("title").toString(),
                    items = subList
                )
            }

            return data!!
        } catch (e: Exception) {
            myLog(e.message ?: "")
            return null
        }
    }

}