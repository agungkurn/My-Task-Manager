package id.ak.mytaskmanager

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ak.mytaskmanager.ui_common.navigation.IntentFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun provideIntentFactory(intentGenerator: IntentGenerator): IntentFactory
}