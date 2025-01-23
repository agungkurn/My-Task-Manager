package id.ak.mytaskmanager.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.CreateTask
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.ui_common.base.BaseViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTask,
    getAllTaskStatusUseCase: GetAllTaskStatus
) : BaseViewModel() {
    val taskStatus = getAllTaskStatusUseCase.data
        .onEach {
            if (selectedStatus == null) {
                selectedStatus = it.first()
            }
        }

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var selectedStatus by mutableStateOf<TaskStatusEntity?>(null)

    var saved by mutableStateOf(false)

    fun save() {
        loadOnBackground {
            createTaskUseCase(title, description, selectedStatus?.id ?: 1)
            saved = true
        }
    }
}