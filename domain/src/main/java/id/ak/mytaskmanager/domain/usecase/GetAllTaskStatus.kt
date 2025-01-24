package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTaskStatus @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(): List<TaskStatusEntity> {
        return repository.getStatus()
    }
}