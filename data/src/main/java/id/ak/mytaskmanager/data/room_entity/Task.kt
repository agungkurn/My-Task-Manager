package id.ak.mytaskmanager.data.room_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.ak.mytaskmanager.domain.Mapper
import id.ak.mytaskmanager.domain.entity.TaskEntity
import javax.inject.Inject

@Entity
data class Task(
    val title: String,
    val description: String?,
    val statusId: Int,
    val createdAt: Long,
    val updatedAt: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

class TaskDtoToEntityMapper @Inject constructor() : Mapper<Task, TaskEntity>() {
    override fun map(from: Task): TaskEntity {
        return TaskEntity(
            id = from.id,
            title = from.title,
            description = from.description,
            statusId = from.statusId,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt
        )
    }
}