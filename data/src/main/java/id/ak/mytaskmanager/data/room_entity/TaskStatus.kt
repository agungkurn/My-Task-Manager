package id.ak.mytaskmanager.data.room_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.ak.mytaskmanager.domain.Mapper
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import javax.inject.Inject

@Entity
data class TaskStatus(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    companion object {
        fun getStatus() = arrayOf(
            TaskStatus(name = "Pending"),
            TaskStatus(name = "Completed")
        )
    }
}

class TaskStatusDtoToEntityMapper @Inject constructor() : Mapper<TaskStatus, TaskStatusEntity>() {
    override fun map(from: TaskStatus): TaskStatusEntity {
        return TaskStatusEntity(id = from.id, name = from.name)
    }
}