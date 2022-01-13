package com.paixao.labs.myapplication.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.sheet.UserAgent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFireBase(): Firebase {
        return Firebase
    }

    @Provides
    fun provideFireBaseDataBase(firebase: Firebase): FirebaseDatabase {
        return firebase.database
    }

    @Provides
    fun provideUserHandler(firebaseDatabase: FirebaseDatabase): UserHandler {
        return UserAgent(firebaseDatabase)
    }

    @Provides
    fun retrieveString(): String {
        return "minha string bonitona"
    }
}
