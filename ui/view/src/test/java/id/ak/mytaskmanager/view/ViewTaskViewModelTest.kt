package id.ak.mytaskmanager.view

import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.usecase.DeleteTask
import id.ak.mytaskmanager.domain.usecase.GetAllTaskStatus
import id.ak.mytaskmanager.domain.usecase.GetTaskById
import id.ak.mytaskmanager.domain.usecase.UpdateTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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
class ViewTaskViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Rule
    @JvmField
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var updateTaskUseCase: UpdateTask

    @MockK
    lateinit var getAllTaskStatusUseCase: GetAllTaskStatus

    @MockK
    lateinit var getTaskByIdUseCase: GetTaskById

    @RelaxedMockK
    lateinit var deleteTaskUseCase: DeleteTask

    private val viewModel by lazy {
        ViewTaskViewModel(
            getAllTaskStatusUseCase = getAllTaskStatusUseCase,
            updateTaskUseCase = updateTaskUseCase,
            getTaskByIdUseCase = getTaskByIdUseCase,
            deleteTaskUseCase = deleteTaskUseCase
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
    fun `Test populate data will fill task and task status`() {
        runTest {
            val expectedId = 123
            coEvery {
                getAllTaskStatusUseCase()
            } returns listOf(TaskStatusEntity(id = expectedId, name = ""))
            coEvery { getTaskByIdUseCase.asFlow(any()) } returns flowOf(mockk(relaxed = true))

            viewModel.populateData(expectedId)
            advanceUntilIdle()

            assertEquals(expectedId, viewModel.taskId.value)
            assertNotNull(viewModel.task.firstOrNull())
            assert(viewModel.taskStatus.isNotEmpty())
        }
    }

    @Test
    fun `Test update status success`() {
        runTest {
            viewModel.updateStatus(mockk(relaxed = true), mockk(relaxed = true))
            advanceUntilIdle()

            assert(viewModel.statusChanged)
            coVerify { updateTaskUseCase(any(), any(), any(), any()) }
        }
    }

    @Test
    fun `Test delete success`() {
        runTest {
            val expectedId = 123
            coEvery {
                getAllTaskStatusUseCase()
            } returns listOf(TaskStatusEntity(id = expectedId, name = ""))
            coEvery { getTaskByIdUseCase.asFlow(any()) } returns flowOf(mockk(relaxed = true))
            viewModel.populateData(expectedId)
            advanceUntilIdle()

            assertNotNull(viewModel.taskId.value)

            viewModel.delete()
            advanceUntilIdle()

            assert(viewModel.deleted)
            coVerify { deleteTaskUseCase(expectedId) }
        }
    }
}