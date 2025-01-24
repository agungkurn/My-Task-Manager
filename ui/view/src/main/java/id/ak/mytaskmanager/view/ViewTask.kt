package id.ak.mytaskmanager.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ak.mytaskmanager.common.extension.formatAsLocalizedString
import id.ak.mytaskmanager.ui_common.composable.TaskStatusDropdown
import id.ak.mytaskmanager.ui_common.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewTask(
    taskId: Int,
    close: () -> Unit,
    onNavigateUp: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<ViewTaskViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val task by viewModel.task.collectAsStateWithLifecycle(null)
    val taskStatusOptions by remember {
        derivedStateOf {
            viewModel.taskStatus.map { it.name }
        }
    }
    val selectedStatusIndex = remember(viewModel.taskStatus, task) {
        task?.let { t ->
            viewModel.taskStatus.indexOfFirst { it.id == t.statusId }.takeIf { it > -1 }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.view_status_changed)

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.populateData(taskId)
    }

    LaunchedEffect(viewModel.statusChanged) {
        if (viewModel.statusChanged) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message = snackbarMessage)
        }
    }

    LaunchedEffect(viewModel.deleted) {
        if (viewModel.deleted) {
            close()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    task?.let {
                        Text(text = it.title)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "delete")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        task?.let { task ->
            val lastUpdate = remember(task) {
                task.updatedAt.formatAsLocalizedString(isInMillisecond = true)
            }

            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .consumeWindowInsets(scaffoldPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TaskStatusDropdown(
                    enabled = uiState == UiState.NotLoading,
                    text = task.statusName,
                    items = taskStatusOptions,
                    selectedIndex = selectedStatusIndex,
                    onClick = {
                        viewModel.updateStatus(viewModel.taskStatus[it], task)
                    }
                )
                task.description?.let { description ->
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.view_description),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(text = description, style = MaterialTheme.typography.bodyMedium)
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.view_modified, lastUpdate),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.delete()
                            showDeleteDialog = false
                        }
                    ) {
                        Text(text = stringResource(id.ak.mytaskmanager.ui_common.R.string.action_yes))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text(text = stringResource(id.ak.mytaskmanager.ui_common.R.string.action_no))
                    }
                },
                text = {
                    Text(text = "Delete task ${task?.title}?")
                }
            )
        }
    }
}