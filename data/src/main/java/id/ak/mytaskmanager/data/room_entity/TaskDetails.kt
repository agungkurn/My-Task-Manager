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
    Mapper<TaskDetails?, TaskDetailsEntity?>() {
    override fun map(from: TaskDetails?): TaskDetailsEntity? {
        return from?.let {
            TaskDetailsEntity(
                id = it.id,
                title = it.title,
                description = it.description,
                statusId = it.statusId,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                statusName = it.statusName
            )
        }
    }
}