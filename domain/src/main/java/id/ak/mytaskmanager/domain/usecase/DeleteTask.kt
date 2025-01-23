package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.entity.TaskEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTask @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(taskEntity: TaskEntity) {
        repository.deleteTask(taskEntity)
    }
}