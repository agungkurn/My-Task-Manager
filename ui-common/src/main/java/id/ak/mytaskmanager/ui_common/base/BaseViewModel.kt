package id.ak.mytaskmanager.ui_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ak.mytaskmanager.ui_common.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val uiState: StateFlow<UiState>
        field = MutableStateFlow<UiState>(UiState.NotLoading)

    fun onErrorShown() {
        uiState.value = UiState.NotLoading
    }

    protected fun loadOnBackground(
        onLoading: (Boolean) -> Unit = {
            uiState.value = if (it) UiState.Loading else UiState.NotLoading
        },
        onError: (Exception) -> Unit = {
            uiState.value = UiState.Error(it.message ?: "Unknown Error")
            it.printStackTrace()
        },
        action: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            onLoading(true)
            try {
                action()
            } catch (e: Exception) {
                onError(e)
            } finally {
                onLoading(false)
            }
        }
    }
}