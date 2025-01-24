package id.ak.mytaskmanager.edit

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
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getAllTaskStatusUseCase: GetAllTaskStatus,
    private val updateTaskUseCase: UpdateTask,
    private val getTaskByIdUseCase: GetTaskById
) : BaseViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedStatus by mutableStateOf<TaskStatusEntity?>(null)

    var existingData by mutableStateOf<TaskDetailsEntity?>(null)
        private set
    var saved by mutableStateOf(false)

    var taskStatus by mutableStateOf(listOf<TaskStatusEntity>())
        private set

    fun prefill(taskId: Int) {
        loadOnBackground {
            taskStatus = getAllTaskStatusUseCase()
            existingData = getTaskByIdUseCase.asOneShot(taskId)
            existingData?.let { details ->
                title = details.title
                description = details.description.orEmpty()
                selectedStatus = taskStatus.firstOrNull { it.id == details.statusId }
            }
        }

    }

    fun save() {
        loadOnBackground {
            existingData?.let {
                updateTaskUseCase(it.id, title, description, selectedStatus?.id ?: 1)
                saved = true
            }
        }
    }
}