package id.ak.mytaskmanager.home

import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.domain.usecase.GetAllTasks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Rule
    @JvmField
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var getAllTaskStatusUseCase: GetAllTaskStatus

    @MockK
    lateinit var getAllTasksUseCase: GetAllTasks

    private val viewModel by lazy {
        HomeViewModel(
            getAllTaskStatusUseCase = getAllTaskStatusUseCase,
            getAllTasksUseCase = getAllTasksUseCase
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Test fetch task status will set default status and fetch the tasks`() {
        runTest {
            coEvery { getAllTaskStatusUseCase() } returns listOf(mockk(relaxed = true))
            coEvery { getAllTasksUseCase(any()) } returns flowOf(listOf(mockk(relaxed = true)))

            viewModel.fetchTaskStatus()

            advanceUntilIdle()

            assert(viewModel.taskStatus.isNotEmpty())
            assertFalse(viewModel.tasks.firstOrNull().isNullOrEmpty())
        }
    }

    @Test
    fun `Test set status will change selected status and fetch the tasks`() {
        runTest {
            val expectedStatus = listOf(TaskStatusEntity(1, "two"), TaskStatusEntity(2, "one"))
            coEvery {
                getAllTaskStatusUseCase()
            } returns expectedStatus
            coEvery { getAllTasksUseCase(any()) } returns flowOf(listOf(mockk(relaxed = true)))

            viewModel.setStatus(expectedStatus[1])
            advanceUntilIdle()

            assertEquals(expectedStatus[1], viewModel.selectedTaskStatus.value.first())
            assertFalse(viewModel.tasks.firstOrNull().isNullOrEmpty())
        }
    }
}