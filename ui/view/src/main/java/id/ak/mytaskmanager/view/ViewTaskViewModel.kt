package id.ak.mytaskmanager.view

import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.domain.usecase.GetTaskById
import id.ak.mytaskmanager.ui_common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ViewTaskViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskById,
    private val getAllTaskStatusUseCase: GetAllTaskStatus
) : BaseViewModel() {
    val taskId: StateFlow<Int?>
        field = MutableStateFlow<Int?>(null)

    val task = taskId.flatMapLatest {
        it?.let { id ->
            getTaskByIdUseCase(id)
        } ?: flowOf(null)
    }

    val status = task.flatMapLatest {
        it?.let { task ->
            getAllTaskStatusUseCase.data.map {
                it.firstOrNull { it.id == task.statusId }
            }
        } ?: flowOf(null)
    }

    fun setId(taskId: Int) {
        this.taskId.value = taskId
    }
}