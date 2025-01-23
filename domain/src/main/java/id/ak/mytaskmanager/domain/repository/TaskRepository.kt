package id.ak.mytaskmanager.domain.repository

import id.ak.mytaskmanager.domain.entity.TaskEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val status: Flow<List<TaskStatusEntity>>
    val pendingTasks: Flow<List<TaskEntity>>
    val completedTasks: Flow<List<TaskEntity>>

    suspend fun getTaskById(id: Int): TaskEntity
    suspend fun createTask(taskEntity: TaskEntity)
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
}