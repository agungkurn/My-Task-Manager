package id.ak.mytaskmanager.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ak.mytaskmanager.common.extension.formatAsLocalizedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewTask(
    taskId: Int,
    onNavigateUp: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<ViewTaskViewModel>()
    val task by viewModel.task.collectAsStateWithLifecycle(null)
    val status by viewModel.status.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        viewModel.setId(taskId)
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
                }
            )
        }
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
                status?.let { status ->
                    Text(
                        text = stringResource(R.string.view_status),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(text = status.name, style = MaterialTheme.typography.bodyMedium)
                }
                task.description.takeIf { !it.isNullOrBlank() }?.let { description ->
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
    }
}