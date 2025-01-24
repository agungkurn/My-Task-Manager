package id.ak.mytaskmanager.ui_common.navigation

import android.content.Context
import android.content.Intent

interface IntentFactory {
    fun createTask(context: Context): Intent
    fun viewTask(context: Context, taskId: Int): Intent
    fun editTask(context: Context, taskId: Int): Intent

    object Extras {
        const val TASK_ID = "extra:task-id"
    }
}