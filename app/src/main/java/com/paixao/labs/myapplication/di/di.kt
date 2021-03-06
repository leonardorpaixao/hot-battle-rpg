package com.paixao.labs.myapplication.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.data.UserAgent
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.sheet.SheetScreenModel
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
    fun retrieveUserAgent(firebaseDatabase: FirebaseDatabase): UserAgent {
        return UserAgent(firebaseDatabase)
    }
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class HiltModule {

    @Binds
    @IntoMap
    @ScreenModelKey(SheetScreenModel::class)
    abstract fun bindHiltScreenModel(hiltListScreenModel: SheetScreenModel): ScreenModel

    @Binds
    abstract fun bindAnalyticsService(userAgent: UserAgent): UserHandler
}
