package id.ak.mytaskmanager.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ak.mytaskmanager.ui_common.composable.ConfirmChangesDialog
import id.ak.mytaskmanager.ui_common.composable.TaskStatusDropdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateTask(
    close: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<CreateTaskViewModel>()
    val taskStatus by viewModel.taskStatus.collectAsStateWithLifecycle(listOf())
    val taskStatusOptions by remember {
        derivedStateOf {
            taskStatus.map { it.name }
        }
    }
    val selectedStatusIndex = remember(taskStatus, viewModel.selectedStatus) {
        taskStatus.indexOf(viewModel.selectedStatus).takeIf { it > -1 }
    }

    val hasUnsavedChanges by remember {
        derivedStateOf {
            viewModel.title.isNotBlank()
        }
    }
    var showUnsavedChangesDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.saved) {
        if (viewModel.saved) {
            close()
        }
    }

    BackHandler(enabled = hasUnsavedChanges) {
        showUnsavedChangesDialog = true
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.create_title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        floatingActionButton = {
            if (viewModel.title.isNotBlank() && viewModel.selectedStatus != null) {
                FloatingActionButton(onClick = viewModel::save) {
                    Icon(Icons.Default.Check, contentDescription = "done")
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskStatusDropdown(
                text = viewModel.selectedStatus?.name
                    ?: stringResource(R.string.title_status_placeholder),
                items = taskStatusOptions,
                selectedIndex = selectedStatusIndex,
                onClick = {
                    viewModel.selectedStatus = taskStatus[it]
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text(text = stringResource(R.string.create_title_label)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = { Text(text = stringResource(R.string.create_description_label)) },
                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
        }

        if (showUnsavedChangesDialog) {
            ConfirmChangesDialog(
                onDismiss = { showUnsavedChangesDialog = false },
                onConfirm = {
                    viewModel.save()
                    showUnsavedChangesDialog = false
                    close()
                },
                onDeny = {
                    showUnsavedChangesDialog = false
                    close()
                }
            )
        }
    }
}