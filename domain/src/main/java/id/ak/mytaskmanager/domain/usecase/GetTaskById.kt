package id.ak.mytaskmanager.domain.usecase

import id.ak.mytaskmanager.domain.entity.TaskEntity
import id.ak.mytaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskById @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(id: Int): Flow<TaskEntity> {
        return repository.getTaskById(id)
    }
}