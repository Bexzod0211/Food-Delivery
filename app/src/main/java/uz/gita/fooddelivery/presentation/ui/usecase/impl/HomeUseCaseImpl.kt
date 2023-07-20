package uz.gita.fooddelivery.presentation.ui.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.usecase.HomeUseCase
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val repository: AppRepository
):HomeUseCase{
    override fun getALlData(): Flow<Result<List<CategoryData>>> {
        return repository.getAllData()
    }

    override fun selectOrUnselectCategory(categoryTitle: String,select:Boolean): Flow<Result<List<CategoryData>>> {
        return repository.selectOrUnselectCategory(categoryTitle,select)
    }



    override fun getAllCategoriesTitle(): Flow<Result<List<CategoryData>>> {
        return repository.getAllCategoriesTitle()
    }

    override val selectedList: List<String>
        get() = repository.selectedList

}