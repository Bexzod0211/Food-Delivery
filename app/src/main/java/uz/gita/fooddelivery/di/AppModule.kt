package uz.gita.fooddelivery.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddelivery.data.source.local.LocalDatabase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFireStore():FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideLocalDatabase(@ApplicationContext context:Context):LocalDatabase = Room
        .databaseBuilder(context,LocalDatabase::class.java,"foodDelivery.db")
        .allowMainThreadQueries()
        .build()


    @Provides
    fun provideFirebaseAuth():FirebaseAuth = Firebase.auth
}