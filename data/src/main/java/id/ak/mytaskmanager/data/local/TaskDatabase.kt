package id.ak.mytaskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ak.mytaskmanager.data.room_entity.Task
import id.ak.mytaskmanager.data.room_entity.TaskStatus

@Database(entities = [Task::class, TaskStatus::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}