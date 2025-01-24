package id.ak.mytaskmanager.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.ak.mytaskmanager.data.local.TaskDao
import id.ak.mytaskmanager.data.local.TaskDatabase
import id.ak.mytaskmanager.data.room_entity.Task
import id.ak.mytaskmanager.data.room_entity.TaskStatus
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CrudTest {
    private lateinit var dao: TaskDao
    private lateinit var database: TaskDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).build()
        dao = database.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun readWrite() {
        runBlocking {
            val expectedTaskStatus = TaskStatus(name = "Status 123", id = 1)
            val expectedTask = Task(
                title = "abc",
                description = null,
                statusId = 1,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                id = 3
            )

            // write
            dao.populateStatus(expectedTaskStatus)
            dao.createTask(expectedTask)

            // read
            val actualTask = dao.getTaskById(expectedTask.id)
            assertEquals(expectedTask.id, actualTask.id)
        }
    }

    @Test
    fun update() {
        runBlocking {
            val expectedTaskStatus = TaskStatus(name = "Status 123", id = 1)
            val expectedTask = Task(
                title = "abc",
                description = null,
                statusId = 1,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                id = 3
            )

            // write
            dao.populateStatus(expectedTaskStatus)
            dao.createTask(expectedTask)

            // update
            val updatedTitle = "title new"
            dao.updateTask(
                id = expectedTask.id,
                title = updatedTitle,
                description = expectedTask.description,
                statusId = expectedTask.statusId,
                updatedAt = System.currentTimeMillis()
            )
            val actualTask = dao.getTaskById(expectedTask.id)
            assertEquals(updatedTitle, actualTask.title)
        }
    }

    @Test
    fun delete() {
        runBlocking {
            val expectedTaskStatus = TaskStatus(name = "Status 123", id = 1)
            val expectedTask = Task(
                title = "abc",
                description = null,
                statusId = 1,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                id = 3
            )

            // write
            dao.populateStatus(expectedTaskStatus)
            dao.createTask(expectedTask)

            // delete
            dao.deleteTask(taskId = expectedTask.id)

            val actualTask = dao.getTaskById(expectedTask.id)
            assertNull(actualTask)
        }
    }
}