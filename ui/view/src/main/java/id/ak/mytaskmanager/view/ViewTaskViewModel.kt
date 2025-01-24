package id.ak.mytaskmanager.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.domain.usecase.GetTaskById
import id.ak.mytaskmanager.domain.usecase.UpdateTask
import id.ak.mytaskmanager.ui_common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ViewTaskViewModel @Inject constructor(
    getAllTaskStatusUseCase: GetAllTaskStatus,
    private val getTaskByIdUseCase: GetTaskById,
    private val updateTaskUseCase: UpdateTask
) : BaseViewModel() {
    val taskId: StateFlow<Int?>
        field = MutableStateFlow<Int?>(null)

    val task = taskId.flatMapLatest {
        it?.let { id ->
            getTaskByIdUseCase.asFlow(id)
        } ?: flowOf(null)
    }

    val statusOptions = getAllTaskStatusUseCase.data

    var statusChanged by mutableStateOf(false)

    fun setId(taskId: Int) {
        this.taskId.value = taskId
    }

    fun updateStatus(taskStatusEntity: TaskStatusEntity, existingData: TaskDetailsEntity) {
        loadOnBackground {
            statusChanged = false
            updateTaskUseCase(
                id = existingData.id,
                title = existingData.title,
                description = existingData.description,
                statusId = taskStatusEntity.id
            )
            statusChanged = true
        }
    }
}