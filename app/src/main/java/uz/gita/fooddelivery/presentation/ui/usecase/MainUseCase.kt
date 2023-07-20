package uz.gita.fooddelivery.presentation.ui.usecase

import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getBadgesCount():Flow<Int>
}