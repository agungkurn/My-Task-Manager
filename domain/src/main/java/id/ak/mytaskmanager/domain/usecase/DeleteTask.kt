package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTask @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: Int) {
        repository.deleteTask(taskId)
    }
}