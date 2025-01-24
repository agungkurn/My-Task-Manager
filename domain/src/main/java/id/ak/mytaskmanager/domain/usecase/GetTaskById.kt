package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import id.ak.mytaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskById @Inject constructor(private val repository: TaskRepository) {
    fun asFlow(id: Int): Flow<TaskDetailsEntity> {
        return repository.getTaskByIdFlow(id)
    }

    suspend fun asOneShot(id: Int): TaskDetailsEntity {
        return repository.getTaskById(id)
    }
}