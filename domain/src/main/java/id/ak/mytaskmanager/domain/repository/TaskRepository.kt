package id.ak.mytaskmanager.domain.repository

import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import id.ak.mytaskmanager.domain.entity.TaskEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val status: Flow<List<TaskStatusEntity>>
    val pendingTasks: Flow<List<TaskEntity>>
    val completedTasks: Flow<List<TaskEntity>>

    fun getTaskByIdFlow(id: Int): Flow<TaskDetailsEntity?>
    suspend fun getTaskById(id: Int): TaskDetailsEntity?
    suspend fun createTask(title: String, description: String?, statusId: Int)
    suspend fun updateTask(
        id: Int,
        title: String,
        description: String?,
        statusId: Int
    )

    suspend fun deleteTask(taskId: Int)
}