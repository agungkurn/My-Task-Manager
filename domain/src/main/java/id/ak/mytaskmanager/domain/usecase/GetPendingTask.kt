package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetPendingTask @Inject constructor(repository: TaskRepository) {
    val data = repository.pendingTasks
}