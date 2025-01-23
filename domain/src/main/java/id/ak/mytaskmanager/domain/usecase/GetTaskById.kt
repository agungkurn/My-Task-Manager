package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskById @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int) {
        repository.getTaskById(id)
    }
}