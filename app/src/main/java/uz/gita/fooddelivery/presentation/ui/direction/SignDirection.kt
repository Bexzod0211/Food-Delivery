package uz.gita.fooddelivery.presentation.ui.direction

import uz.gita.fooddelivery.navigation.AppNavigator
import uz.gita.fooddelivery.presentation.ui.screens.main.MainScreen
import uz.gita.fooddelivery.presentation.ui.screens.sign.SignContract
import javax.inject.Inject

class SignDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : SignContract.Direction{
    override suspend fun openMainScreen() {
        appNavigator.replace(MainScreen())
    }
}