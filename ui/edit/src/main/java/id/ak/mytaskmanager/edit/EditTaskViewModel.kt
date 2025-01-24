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
    getAllTaskStatusUseCase: GetAllTaskStatus,
    private val updateTaskUseCase: UpdateTask,
    private val getTaskByIdUseCase: GetTaskById
) : BaseViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedStatus by mutableStateOf<TaskStatusEntity?>(null)
    var saved by mutableStateOf(false)

    val taskStatus = getAllTaskStatusUseCase.data

    private var taskDetails: TaskDetailsEntity? = null

    fun prefill(taskId: Int) {
        loadOnBackground {
            taskDetails = getTaskByIdUseCase.asOneShot(taskId)
            taskDetails?.let {
                title = it.title
                description = it.description.orEmpty()
                selectedStatus = TaskStatusEntity(it.statusId, it.statusName)
            }
        }

    }

    fun save() {
        loadOnBackground {
            taskDetails?.let {
                updateTaskUseCase(it.id, title, description, selectedStatus?.id ?: 1)
                saved = true
            }
        }
    }
}