package uz.gita.fooddelivery.data.model

data class MyOrderData(
    val id:Int,
    val imageUrl:String,
    val title:String,
    val totalPrice:String,
    val count:Int,
    val uid:String,
)
