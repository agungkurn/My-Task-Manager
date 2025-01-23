package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class CreateTask @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(title: String, description: String?, statusId: Int) {
        repository.createTask(title = title, description = description, statusId = statusId)
    }
}