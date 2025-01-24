package id.ak.mytaskmanager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.ak.mytaskmanager.data.room_entity.Task
import id.ak.mytaskmanager.data.room_entity.TaskDetails
import id.ak.mytaskmanager.data.room_entity.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskStatus")
    suspend fun getAllStatus(): List<TaskStatus>

    @Query(
        "SELECT t.id, t.title, t.description, t.createdAt, t.updatedAt, t.statusId, s.name AS statusName " +
                "FROM Task AS t " +
                "INNER JOIN TaskStatus AS s " +
                "ON t.statusId = s.id " +
                "WHERE t.statusId IN (:statusIds)" +
                "ORDER BY t.updatedAt DESC"
    )
    fun getAllTasks(statusIds: List<Int>): Flow<List<TaskDetails>>

    @Query(
        "SELECT t.id, t.title, t.description, t.createdAt, t.updatedAt, t.statusId, s.name AS statusName " +
                "FROM Task AS t " +
                "INNER JOIN TaskStatus AS s " +
                "ON t.statusId = s.id " +
                "WHERE t.id = :id"
    )
    fun getTaskByIdFlow(id: Int): Flow<TaskDetails>

    @Query(
        "SELECT t.id, t.title, t.description, t.createdAt, t.updatedAt, t.statusId, s.name AS statusName " +
                "FROM Task AS t " +
                "INNER JOIN TaskStatus AS s " +
                "ON t.statusId = s.id " +
                "WHERE t.id = :id"
    )
    suspend fun getTaskById(id: Int): TaskDetails

    @Insert
    suspend fun createTask(task: Task)

    @Insert
    suspend fun populateStatus(vararg status: TaskStatus)

    @Query(
        "UPDATE Task " +
                "SET title = :title, description = :description, statusId = :statusId, updatedAt = :updatedAt " +
                "WHERE id = :id"
    )
    suspend fun updateTask(
        id: Int,
        title: String,
        description: String?,
        statusId: Int,
        updatedAt: Long
    )

    @Query("DELETE FROM Task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}