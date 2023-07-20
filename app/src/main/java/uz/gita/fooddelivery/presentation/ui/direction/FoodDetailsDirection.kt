package uz.gita.fooddelivery.presentation.ui.direction

import uz.gita.fooddelivery.navigation.AppNavigator
import uz.gita.fooddelivery.presentation.ui.pages.cart.CartScreen
import uz.gita.fooddelivery.presentation.ui.screens.details.FoodDetailsContract
import uz.gita.fooddelivery.presentation.ui.screens.main.MainScreen
import javax.inject.Inject

class FoodDetailsDirection @Inject constructor(
    private val appNavigator: AppNavigator
):FoodDetailsContract.Direction{
    override suspend fun openHomeScreen() {
        appNavigator.navigateTo(MainScreen())
    }

    override suspend fun openCartScreen() {
        appNavigator.replace(MainScreen(CartScreen()))
    }

    override suspend fun back() {
        appNavigator.pop()
    }
}