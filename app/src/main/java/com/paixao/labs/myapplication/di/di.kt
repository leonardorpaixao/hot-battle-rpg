package com.paixao.labs.myapplication.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
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
class ActivityModule {
    @Provides
    fun retrieveString(): String {
        return "TESTE"
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class HiltModule {

    @Binds
    @IntoMap
    @ScreenModelKey(SheetScreenModel::class)
    abstract fun bindHiltScreenModel(hiltListScreenModel: SheetScreenModel): ScreenModel

    @Binds
    abstract fun bindAnalyticsService(userAgent: UserAgent): UserHandler
}
