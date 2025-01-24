package id.ak.mytaskmanager.create

import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.CreateTask
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CreateTaskViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Rule
    @JvmField
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var createTaskUseCase: CreateTask

    @MockK
    lateinit var getAllTaskStatusUseCase: GetAllTaskStatus

    private val viewModel by lazy {
        CreateTaskViewModel(
            createTaskUseCase = createTaskUseCase,
            getAllTaskStatusUseCase = getAllTaskStatusUseCase
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
    fun `Test fetch task status success`() {
        runTest {
            coEvery {
                getAllTaskStatusUseCase()
            } returns listOf(TaskStatusEntity(id = 1, name = ""))

            viewModel.fetchTaskStatus()

            advanceUntilIdle()

            val actual = viewModel.taskStatus
            assert(actual.isNotEmpty())
            coVerify { getAllTaskStatusUseCase() }
        }
    }

    @Test
    fun `Test save success`() {
        runTest {
            viewModel.title = "title"
            viewModel.description = ""
            viewModel.selectedStatus = mockk<TaskStatusEntity>(relaxed = true)
            viewModel.save()

            advanceUntilIdle()

            coVerify { createTaskUseCase.invoke(any(), any(), any()) }
            assertEquals(true, viewModel.saved)
        }
    }
}