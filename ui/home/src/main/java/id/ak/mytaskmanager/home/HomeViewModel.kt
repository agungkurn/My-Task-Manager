package id.ak.mytaskmanager.home

import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.domain.usecase.GetAllTasks
import id.ak.mytaskmanager.ui_common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllTaskStatusUseCase: GetAllTaskStatus,
    private val getAllTasksUseCase: GetAllTasks
) : BaseViewModel() {
    val selectedTaskStatus: StateFlow<List<TaskStatusEntity>>
        field = MutableStateFlow(listOf())

    val taskStatus = getAllTaskStatusUseCase.data
        .onEach {
            if (selectedTaskStatus.value.isNotEmpty()) return@onEach
            selectedTaskStatus.value = it
        }

    val tasks = selectedTaskStatus.flatMapLatest {
        val ids = it.map { it.id }
        getAllTasksUseCase(ids)
    }

    fun setStatus(taskStatusEntity: TaskStatusEntity) {
        selectedTaskStatus.value = selectedTaskStatus.value.let {
            if (taskStatusEntity in it) {
                if (it.size == 1) return@let it
                it.filter { it.id != taskStatusEntity.id }
            } else {
                it + listOf(taskStatusEntity)
            }
        }
    }
}