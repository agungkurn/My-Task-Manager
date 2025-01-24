package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTask @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(
        id: Int,
        title: String,
        description: String?,
        statusId: Int
    ) {
        repository.updateTask(
            id = id,
            title = title,
            description = description,
            statusId = statusId
        )
    }
}