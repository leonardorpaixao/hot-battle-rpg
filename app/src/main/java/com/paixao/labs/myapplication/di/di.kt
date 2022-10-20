package com.paixao.labs.myapplication.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.data.UserAgent
import com.paixao.labs.myapplication.data.session.SessionManager
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.home.HomeStepModel
import com.paixao.labs.myapplication.ui.login.LoginScreenModel
import com.paixao.labs.myapplication.ui.sheet.CharacterDetailsScreenModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
internal class ActivityModule {

 /*   @Provides
    fun retrieveFirebaseService(): Firebase = Firebase*/

    /*@Provides
    fun retrieveFirebaseDatabase(firebase: Firebase): FirebaseDatabase =
        firebase.database

    @Provides
    fun retrieveFirebaseFireStore(firebase: Firebase): FirebaseFirestore = firebase.firestore*/



   /* @Provides
    fun retrieveSessionManager(
        userHandler: UserAgent
    ): SessionManager = SessionManager(userHandler = userHandler)*/
}

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonModule{

    @Provides
    @Singleton
    fun retrieveUserAgent(
    ): UserAgent {
        return UserAgent(Firebase.database, Firebase.firestore)
    }
    @Provides
    @Singleton
    fun retrieveSessionManager(
        userHandler: UserAgent
    ): SessionManager = SessionManager(userHandler = userHandler)
}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class ModelsAndServices {

    @Binds
    abstract fun bindUserHandler(userAgent: UserAgent): UserHandler

    @Binds
    abstract fun bindSessionHandler(sessionManager: SessionManager): SessionHandler

    @Binds
    @IntoMap
    @ScreenModelKey(HomeStepModel::class)
    abstract fun bindHomeScreenModel(hiltListScreenModel: HomeStepModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(CharacterDetailsScreenModel::class)
    abstract fun bindCharacterDetailsScreenModel(hiltListScreenModel: CharacterDetailsScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(LoginScreenModel::class)
    abstract fun bindLoginScreenModel(loginScreenModel: LoginScreenModel): ScreenModel
}

