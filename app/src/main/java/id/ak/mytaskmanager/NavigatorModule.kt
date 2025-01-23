package id.ak.mytaskmanager

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ak.mytaskmanager.ui_common.navigation.ActivityDictionary
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {
    @Binds
    @Singleton
    fun provideActivityDictionary(defaultDictionary: DefaultDictionary): ActivityDictionary
}