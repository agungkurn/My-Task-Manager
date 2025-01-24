package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import id.ak.mytaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasks @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(statusIds: List<Int>): Flow<List<TaskDetailsEntity>> {
        val ids = statusIds.toList()
        return repository.getTasks(ids)
    }
}