package id.ak.mytaskmanager.data.room_entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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