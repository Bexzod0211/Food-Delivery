package uz.gita.fooddelivery.presentation.ui.usecase

import kotlinx.coroutines.flow.Flow

interface SignUseCase {
    fun login(email:String,password:String):Flow<Result<String>>
    fun signUp(firstName:String,lastName:String,email: String,password: String):Flow<Result<String>>
}