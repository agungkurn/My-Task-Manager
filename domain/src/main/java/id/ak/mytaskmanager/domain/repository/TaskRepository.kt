package id.ak.mytaskmanager.domain.repository

import id.ak.mytaskmanager.domain.entity.TaskEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val status: Flow<List<TaskStatusEntity>>
    val pendingTasks: Flow<List<TaskEntity>>
    val completedTasks: Flow<List<TaskEntity>>

    fun getTaskById(id: Int): Flow<TaskEntity>
    suspend fun createTask(title: String, description: String?, statusId: Int)
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
}