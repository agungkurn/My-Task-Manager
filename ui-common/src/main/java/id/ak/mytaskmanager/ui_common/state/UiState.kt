package id.ak.mytaskmanager.ui_common.state

sealed interface UiState {
    object Loading : UiState
    object NotLoading : UiState
    data class Error(val message: String) : UiState
}