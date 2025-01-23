package id.ak.mytaskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ak.mytaskmanager.data.room_entity.Task
import id.ak.mytaskmanager.data.room_entity.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskStatus")
    fun getAllStatus() : Flow<List<TaskStatus>>

    @Query("SELECT * FROM Task WHERE statusId = :statusId")
    fun getAllTasks(statusId: Int) : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun getTaskById(id: Int) : Task

    @Insert
    suspend fun createTask(task: Task)

    @Insert
    suspend fun populateStatus(vararg status: TaskStatus)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}