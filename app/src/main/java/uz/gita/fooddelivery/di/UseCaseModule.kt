package uz.gita.fooddelivery.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddelivery.presentation.ui.usecase.CartUseCase
import uz.gita.fooddelivery.presentation.ui.usecase.FoodDetailsUseCase
import uz.gita.fooddelivery.presentation.ui.usecase.HomeUseCase
import uz.gita.fooddelivery.presentation.ui.usecase.MainUseCase
import uz.gita.fooddelivery.presentation.ui.usecase.SignUseCase
import uz.gita.fooddelivery.presentation.ui.usecase.impl.CartUseCaseImpl
import uz.gita.fooddelivery.presentation.ui.usecase.impl.FoodDetailsUseCaseImpl
import uz.gita.fooddelivery.presentation.ui.usecase.impl.HomeUseCaseImpl
import uz.gita.fooddelivery.presentation.ui.usecase.impl.MainUseCaseImpl
import uz.gita.fooddelivery.presentation.ui.usecase.impl.SignUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindHomeUseCase(impl:HomeUseCaseImpl):HomeUseCase

    @Binds
    fun bindFoodDetailsUseCase(impl:FoodDetailsUseCaseImpl):FoodDetailsUseCase

    @Binds
    fun bindCartUseCase(impl:CartUseCaseImpl):CartUseCase

    @Binds
    fun bindMainUseCase(impl:MainUseCaseImpl):MainUseCase

    @Binds
    fun bindSignUseCase(impl:SignUseCaseImpl):SignUseCase
}