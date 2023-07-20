package uz.gita.fooddelivery.presentation.ui.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.model.CategoryData

interface HomeUseCase {
    fun getALlData():Flow<Result<List<CategoryData>>>
    fun selectOrUnselectCategory(categoryTitle:String,select:Boolean):Flow<Result<List<CategoryData>>>
    fun getAllCategoriesTitle():Flow<Result<List<CategoryData>>>
    val selectedList:List<String>
}