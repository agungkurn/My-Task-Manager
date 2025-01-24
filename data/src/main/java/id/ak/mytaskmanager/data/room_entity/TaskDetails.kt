package id.ak.mytaskmanager.data.room_entity

import androidx.room.Entity
import id.ak.mytaskmanager.domain.Mapper
import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import javax.inject.Inject

@Entity
data class TaskDetails(
    val id: Int,
    val title: String,
    val description: String?,
    val statusId: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val statusName: String
)

class TaskDetailsDtoToEntityMapper @Inject constructor() :
    Mapper<TaskDetails, TaskDetailsEntity>() {
    override fun map(from: TaskDetails): TaskDetailsEntity {
        return TaskDetailsEntity(
            id = from.id,
            title = from.title,
            description = from.description,
            statusId = from.statusId,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt,
            statusName = from.statusName
        )
    }
}