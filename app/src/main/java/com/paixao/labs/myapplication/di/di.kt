package com.paixao.labs.myapplication.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.data.UserAgent
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.home.HomeStepModel
import com.paixao.labs.myapplication.ui.sheet.CharacterDetailsScreenModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
internal class ActivityModule {

    @Provides
    fun retrieveFirebaseService(): Firebase = Firebase

    @Provides
    fun retrieveFirebaseDatabase(firebase: Firebase): FirebaseDatabase =
        firebase.database

    @Provides
    fun retrieveFirebaseFireStore(firebase: Firebase): FirebaseFirestore = firebase.firestore

    @Provides
    fun retrieveUserAgent(
        firebaseDatabase: FirebaseDatabase,
        firebaseFirestore: FirebaseFirestore
    ): UserAgent {
        return UserAgent(firebaseDatabase, firebaseFirestore)
    }
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class HiltModule {

    @Binds
    abstract fun bindAnalyticsService(userAgent: UserAgent): UserHandler

    @Binds
    @IntoMap
    @ScreenModelKey(HomeStepModel::class)
    abstract fun bindHiltHomeStepModel(hiltListScreenModel: HomeStepModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(CharacterDetailsScreenModel::class)
    abstract fun bindCharacterDetailsScreenModel(hiltListScreenModel: CharacterDetailsScreenModel): ScreenModel
}

