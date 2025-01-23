package id.ak.mytaskmanager.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ak.mytaskmanager.data.repository.DefaultTaskRepository
import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideTaskRepository(taskRepository: DefaultTaskRepository): TaskRepository
}