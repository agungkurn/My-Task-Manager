package id.ak.mytaskmanager.data.repository

import id.ak.mytaskmanager.data.local.TaskDao
import id.ak.mytaskmanager.data.room_entity.Task
import id.ak.mytaskmanager.data.room_entity.TaskDetailsDtoToEntityMapper
import id.ak.mytaskmanager.data.room_entity.TaskDtoToEntityMapper
import id.ak.mytaskmanager.data.room_entity.TaskEntityToDtoMapper
import id.ak.mytaskmanager.data.room_entity.TaskStatus
import id.ak.mytaskmanager.data.room_entity.TaskStatusDtoToEntityMapper
import id.ak.mytaskmanager.domain.entity.TaskDetailsEntity
import id.ak.mytaskmanager.domain.entity.TaskEntity
import id.ak.mytaskmanager.domain.entity.TaskStatusEntity
import id.ak.mytaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskDtoToEntityMapper: TaskDtoToEntityMapper,
    private val taskStatusDtoToEntityMapper: TaskStatusDtoToEntityMapper,
    private val taskEntityToDtoMapper: TaskEntityToDtoMapper,
    private val taskDetailsDtoToEntityMapper: TaskDetailsDtoToEntityMapper
) : TaskRepository {
    override val pendingTasks = taskDao.getAllTasks(statusId = 1)
        .map<List<Task>, List<TaskEntity>>(taskDtoToEntityMapper::map)

    override val completedTasks = taskDao.getAllTasks(statusId = 2)
        .map<List<Task>, List<TaskEntity>>(taskDtoToEntityMapper::map)

    override val status = taskDao.getAllStatus()
        .map<List<TaskStatus>, List<TaskStatusEntity>> {
            if (it.isNotEmpty()) {
                taskStatusDtoToEntityMapper.map(it)
            } else {
                taskDao.populateStatus(*TaskStatus.getStatus())
                listOf()
            }
        }

    override fun getTaskByIdFlow(id: Int): Flow<TaskDetailsEntity> {
        return taskDao.getTaskByIdFlow(id).map {
            taskDetailsDtoToEntityMapper.map(it)
        }
    }

    override suspend fun getTaskById(id: Int): TaskDetailsEntity {
        return taskDao.getTaskById(id).let {
            taskDetailsDtoToEntityMapper.map(it)
        }
    }

    override suspend fun createTask(title: String, description: String?, statusId: Int) {
        taskDao.createTask(
            Task(
                title = title,
                description = description,
                statusId = statusId,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun updateTask(
        id: Int,
        title: String,
        description: String?,
        statusId: Int
    ) {
        taskDao.updateTask(
            id = id,
            title = title,
            description = description,
            statusId = statusId,
            updatedAt = System.currentTimeMillis()
        )
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        val converted = taskEntityToDtoMapper.map(taskEntity)
        taskDao.deleteTask(converted)
    }
}