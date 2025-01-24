package id.ak.mytaskmanager.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Home(
    openCreateTask: () -> Unit,
    openTaskDetails: (TaskDetailsEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<HomeViewModel>()
    val status by viewModel.taskStatus.collectAsStateWithLifecycle(listOf())
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(listOf())
    val selectedStatus by viewModel.selectedTaskStatus.collectAsStateWithLifecycle()

    val showStatusInList by remember(status, selectedStatus) {
        derivedStateOf { selectedStatus.size == status.size }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.home_title))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = openCreateTask
            ) {
                Icon(Icons.Default.Add, contentDescription = "create task")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                status.forEach {
                    val selected = remember(it, selectedStatus) { it in selectedStatus }

                    FilterChip(
                        selected = selected,
                        label = { Text(text = it.name) },
                        onClick = {
                            viewModel.setStatus(it)
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (tasks.isNotEmpty()) {
                    items(tasks) { task ->
                        TaskItem(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .animateContentSize(),
                            taskEntity = task,
                            showStatus = showStatusInList,
                            onClick = {
                                openTaskDetails(task)
                            }
                        )
                    }
                } else {
                    item {
                        val status = remember(selectedStatus) {
                            selectedStatus.map { it.name }.joinToString()
                        }
                        Text(text = stringResource(R.string.home_empty_task, status))
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    taskEntity: TaskDetailsEntity,
    showStatus: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = taskEntity.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            taskEntity.description?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (showStatus) {
                val background = remember(taskEntity) {
                    when (taskEntity.statusId) {
                        1 -> Color.Gray
                        2 -> Color.Green.copy(green = .5f)
                        else -> Color.Black
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(background)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = taskEntity.statusName, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}