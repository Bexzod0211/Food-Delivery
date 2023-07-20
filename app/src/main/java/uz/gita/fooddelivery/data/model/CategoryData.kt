package uz.gita.fooddelivery.data.model

data class CategoryData(
    val id:Int,
    val title:String,
    val items:List<ProductData>
)
