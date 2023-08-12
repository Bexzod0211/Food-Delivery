package uz.gita.fooddelivery.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.data.model.MyOrderData
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.data.source.local.LocalDatabase
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import uz.gita.fooddelivery.utils.myLog
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val localDb: LocalDatabase,
    private val auth: FirebaseAuth
) : AppRepository {

    private val dao = localDb.getProductDao()
    override val selectedList = mutableListOf<String>()
    private val myOrdersCollection = "My orders"
    override fun getProductsCountInCart(): Flow<Int> {
        return dao.getProductsCount()
    }

    override fun createAccount(email: String, password: String): Flow<Result<String>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(Result.success("You have signed up successfully"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun login(email: String, password: String): Flow<Result<String>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(Result.success("You have logged in successfully"))
            }
            .addOnFailureListener {
                myLog(it.message ?: "")
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun hasLoggedIn(): Flow<Boolean> = flow {
        emit(auth.currentUser != null)
    }

    override fun sendOrders(): Flow<Result<String>> = callbackFlow {
        /*val list = mutableListOf<MyOrderData>()
        dao.getAllProductsFromCart().forEach { productEntity ->
            list.add(
                MyOrderData(
                    id = productEntity.id,
                    imageUrl = productEntity.imageUrl,
                    title = productEntity.title,
                    totalPrice = "${productEntity.totalPrice}",
                    count = productEntity.count,
                    uid = auth.currentUser?.uid!!
                )
            )
        }

        val map: Map<String, Any?> = list.map { order ->
            "id" to order.id
            "imageUrl" to order.
            "title" to order.title
            "totalPrice" to "${order.totalPrice}"
            "count" to order.count
            "uid" to order.uid
            "${order.id}" to order
        }.toMap()



        fireStore
            .collection(myOrdersCollection)
            .add(map)
            .addOnSuccessListener {
                dao.deleteAllProducts()
                trySend(Result.success("Orders have been sent successfully"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()*/

            dao.getAllProductsFromCart().forEach { productEntity ->

                val order = hashMapOf(
                    "id" to productEntity.id,
                    "imageUrl" to productEntity.imageUrl,
                    "title" to productEntity.title,
                    "totalPrice" to "${productEntity.totalPrice}",
                    "count" to productEntity.count,
                    "uid" to auth.currentUser?.uid
                )


                fireStore
                    .collection(myOrdersCollection)
                    .add(order)
                    .addOnSuccessListener {
                        dao.deleteProduct(productEntity)
                    }
                    .addOnFailureListener {
                        trySend(Result.failure(it))
                    }

            }

        myLog("products in cart ${dao.getAllProductsFromCart()}")
        trySend(Result.success("Orders have been sent successfully"))
        awaitClose()

    }

    override fun getMyOrders(): Flow<Result<List<MyOrderData>>> = callbackFlow {
        val list = mutableListOf<MyOrderData>()

        fireStore
            .collection(myOrdersCollection)
            .whereEqualTo("uid", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                documents.forEach {
                    list.add(
                        MyOrderData(
                            id = (it.get("id") as Long).toInt(),
                            imageUrl = it.get("imageUrl") as String,
                            title = it.get("title") as String,
                            totalPrice = it.get("totalPrice") as String,
                            count = (it.get("count") as Long).toInt(),
                            uid = it.get("uid") as String
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