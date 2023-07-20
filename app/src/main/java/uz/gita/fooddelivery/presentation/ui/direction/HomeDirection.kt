package uz.gita.fooddelivery.presentation.ui.direction

import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.navigation.AppNavigator
import uz.gita.fooddelivery.presentation.ui.screens.details.FoodDetailsScreen
import uz.gita.fooddelivery.presentation.ui.pages.home.HomeContract
import javax.inject.Inject

class HomeDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : HomeContract.Direction{
    override suspend fun openDetailsScreen(product: ProductData) {
        appNavigator.navigateTo(FoodDetailsScreen(product))
    }
}