package id.ak.mytaskmanager.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ak.mytaskmanager.domain.entity.TaskEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Home(
    openCreateTask: () -> Unit,
    openTaskDetails: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<HomeViewModel>()
    val status by viewModel.taskStatus.collectAsStateWithLifecycle(listOf())
    val pendingTasks by viewModel.pendingTask.collectAsStateWithLifecycle(listOf())
    val completedTask by viewModel.completedTask.collectAsStateWithLifecycle(listOf())

    val pagerState = rememberPagerState { status.size }
    val coroutineScope = rememberCoroutineScope()

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
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                status.forEachIndexed { i, status ->
                    Tab(
                        selected = pagerState.currentPage == i,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(i)
                            }
                        },
                        text = { Text(text = status.name) }
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                val tasks = when (it) {
                    0 -> pendingTasks
                    1 -> completedTask
                    else -> listOf()
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (tasks.isNotEmpty()) {
                        items(tasks) { task ->
                            TaskItem(
                                modifier = Modifier.fillParentMaxWidth(),
                                taskEntity = task,
                                onClick = {
                                    openTaskDetails(task)
                                }
                            )
                        }
                    } else {
                        item {
                            Text(text = stringResource(R.string.home_empty_task, status[it].name))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    taskEntity: TaskEntity,
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
        }
    }
}