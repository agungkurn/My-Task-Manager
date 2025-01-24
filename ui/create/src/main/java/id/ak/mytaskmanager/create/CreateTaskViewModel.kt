package id.ak.mytaskmanager.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.CreateTask
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.ui_common.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTask,
    private val getAllTaskStatusUseCase: GetAllTaskStatus
) : BaseViewModel() {
    var taskStatus by mutableStateOf(listOf<TaskStatusEntity>())
        private set

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedStatus by mutableStateOf<TaskStatusEntity?>(null)

    var saved by mutableStateOf(false)

    fun fetchTaskStatus() {
        loadOnBackground {
            taskStatus = getAllTaskStatusUseCase()
            selectedStatus = taskStatus.first()
        }
    }

    fun save() {
        loadOnBackground {
            createTaskUseCase(title, description, selectedStatus?.id ?: 1)
            saved = true
        }
    }
}