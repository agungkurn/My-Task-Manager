package id.ak.mytaskmanager.home

import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.domain.usecase.GetCompletedTask
import id.ak.mytaskmanager.domain.usecase.GetPendingTask
import id.ak.mytaskmanager.ui_common.base.BaseViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllTaskStatusUseCase: GetAllTaskStatus,
    getPendingTaskUseCase: GetPendingTask,
    getCompletedTaskUseCase: GetCompletedTask
) : BaseViewModel() {
    val taskStatus = getAllTaskStatusUseCase.data
    val pendingTask = getPendingTaskUseCase.data.distinctUntilChanged()
    val completedTask = getCompletedTaskUseCase.data.distinctUntilChanged()
}