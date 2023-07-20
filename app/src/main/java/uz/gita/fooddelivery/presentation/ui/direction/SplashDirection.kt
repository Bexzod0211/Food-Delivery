package uz.gita.fooddelivery.presentation.ui.direction

import uz.gita.fooddelivery.navigation.AppNavigator
import uz.gita.fooddelivery.presentation.ui.screens.main.MainScreen
import uz.gita.fooddelivery.presentation.ui.screens.sign.SignScreen
import uz.gita.fooddelivery.presentation.ui.screens.splash.SplashContract
import javax.inject.Inject

class SplashDirection @Inject constructor(
    private val appNavigator: AppNavigator
) :SplashContract.Direction{
    override suspend fun openMainScreen() {
        appNavigator.replace(MainScreen())
    }

    override suspend fun openSignScreen() {
        appNavigator.replace(SignScreen())
    }
}