package com.paixao.labs.myapplication.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.data.TableAgent
import com.paixao.labs.myapplication.data.UserAgent
import com.paixao.labs.myapplication.data.session.SessionAgent
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.TableHandler
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.characters.CharactersScreenModel
import com.paixao.labs.myapplication.ui.login.LoginScreenModel
import com.paixao.labs.myapplication.ui.sheet.CharacterScreenModel
import com.paixao.labs.myapplication.ui.table.creationAndEdit.CreateOrEditTableScreenModel
import com.paixao.labs.myapplication.ui.table.session.TableSessionScreenModel
import com.paixao.labs.myapplication.ui.table.tableListing.TableListingModel
import com.paixao.labs.myapplication.ui.table.tableLogin.TableLoginScreenModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonModule {

    @Provides
    @Singleton
    fun retrieveSessionManager(
        userHandler: UserAgent
    ): SessionAgent = SessionAgent(userHandler = userHandler)


    @Provides
    @Singleton
    fun retrieveTableAgent(): TableAgent = TableAgent(
        firebaseFireStore = Firebase.firestore,
    )

    @Provides
    @Singleton
    fun retrieveUserAgent(): UserAgent {
        return UserAgent(
            firebaseDatabase = Firebase.database,
            firebaseFireStore = Firebase.firestore,
        )
    }


}

@Module
@InstallIn(ActivityComponent::class)
internal abstract class ModelsAndServices {

    @Binds
    abstract fun bindUserHandler(userAgent: UserAgent): UserHandler

    @Binds
    abstract fun bindSessionHandler(sessionManager: SessionAgent): SessionHandler

    @Binds
    abstract fun bindTableHandler(tableAgent: TableAgent): TableHandler

    @Binds
    @IntoMap
    @ScreenModelKey(CharacterScreenModel::class)
    abstract fun bindCharacterDetailsScreenModel(model: CharacterScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(LoginScreenModel::class)
    abstract fun bindLoginScreenModel(model: LoginScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(CharactersScreenModel::class)
    abstract fun bindCharactersModel(model: CharactersScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(TableLoginScreenModel::class)
    abstract fun bindTableLoginScreenModel(model: TableLoginScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(TableListingModel::class)
    abstract fun bindTableListingModel(model: TableListingModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(CreateOrEditTableScreenModel::class)
    abstract fun bindCreateOrEditTableScreenModel(model: CreateOrEditTableScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(TableSessionScreenModel::class)
    abstract fun bindTableSessionScreenModel(model: TableSessionScreenModel): ScreenModel
}

