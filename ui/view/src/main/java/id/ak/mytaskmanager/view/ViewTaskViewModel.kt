package id.ak.mytaskmanager.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.DeleteTask
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
    private val getAllTaskStatusUseCase: GetAllTaskStatus,
    private val getTaskByIdUseCase: GetTaskById,
    private val updateTaskUseCase: UpdateTask,
    private val deleteTaskUseCase: DeleteTask
) : BaseViewModel() {
    val taskId: StateFlow<Int?>
        field = MutableStateFlow<Int?>(null)

    val task = taskId.flatMapLatest {
        it?.let { id ->
            getTaskByIdUseCase.asFlow(id)
        } ?: flowOf(null)
    }

    var taskStatus by mutableStateOf(listOf<TaskStatusEntity>())
        private set

    var statusChanged by mutableStateOf(false)
    var deleted by mutableStateOf(false)

    fun populateData(taskId: Int) {
        this.taskId.value = taskId

        loadOnBackground {
            taskStatus = getAllTaskStatusUseCase()
        }
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

    fun delete() {
        loadOnBackground {
            taskId.value?.let {
                deleteTaskUseCase(it)
                deleted = true
            }
        }
    }
}