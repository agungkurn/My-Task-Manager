package id.ak.mytaskmanager.edit

import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
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
class EditTaskViewModelTest {
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

    private val viewModel by lazy {
        EditTaskViewModel(
            getAllTaskStatusUseCase = getAllTaskStatusUseCase,
            updateTaskUseCase = updateTaskUseCase,
            getTaskByIdUseCase = getTaskByIdUseCase
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
    fun `Test prefill success`() {
        runTest {
            coEvery {
                getAllTaskStatusUseCase()
            } returns listOf(TaskStatusEntity(id = 1, name = ""))
            coEvery { getTaskByIdUseCase.asOneShot(any()) } returns mockk(relaxed = true)

            viewModel.prefill(123)

            advanceUntilIdle()

            assert(viewModel.taskStatus.isNotEmpty())
            assertNotNull(viewModel.existingData)
        }
    }

    @Test
    fun `Test save success`() {
        runTest {
            coEvery {
                getAllTaskStatusUseCase()
            } returns listOf(TaskStatusEntity(id = 1, name = ""))
            coEvery { getTaskByIdUseCase.asOneShot(any()) } returns mockk(relaxed = true)
            viewModel.prefill(123)

            viewModel.save()

            advanceUntilIdle()

            coVerify { updateTaskUseCase.invoke(any(), any(), any(), any()) }
            assertEquals(true, viewModel.saved)
        }
    }
}