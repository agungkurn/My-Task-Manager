package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTaskStatus @Inject constructor(repository: TaskRepository) {
    val data = repository.status
}