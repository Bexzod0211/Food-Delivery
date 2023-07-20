package uz.gita.fooddelivery.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddelivery.presentation.ui.direction.FoodDetailsDirection
import uz.gita.fooddelivery.presentation.ui.direction.HomeDirection
import uz.gita.fooddelivery.presentation.ui.direction.SignDirection
import uz.gita.fooddelivery.presentation.ui.direction.SplashDirection
import uz.gita.fooddelivery.presentation.ui.screens.details.FoodDetailsContract
import uz.gita.fooddelivery.presentation.ui.pages.home.HomeContract
import uz.gita.fooddelivery.presentation.ui.screens.sign.SignContract
import uz.gita.fooddelivery.presentation.ui.screens.splash.SplashContract

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @Binds
    fun bindHomeDirection(impl:HomeDirection): HomeContract.Direction

    @Binds
    fun bindFoodDetailsDirection(impl:FoodDetailsDirection):FoodDetailsContract.Direction

    @Binds
    fun bindSplashDirection(impl:SplashDirection):SplashContract.Direction

    @Binds
    fun bindSignDirection(impl:SignDirection):SignContract.Direction
}