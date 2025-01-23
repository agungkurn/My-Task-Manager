package id.ak.mytaskmanager.domain.entity

data class TaskEntity(
    val id: Int,
    val title: String,
    val description: String?,
    val statusId: Int,
    val createdAt: Long,
    val updatedAt: Long
)
