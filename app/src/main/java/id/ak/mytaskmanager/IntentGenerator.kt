package id.ak.mytaskmanager

import android.content.Context
import android.content.Intent
import id.ak.mytaskmanager.create.CreateTaskActivity
import id.ak.mytaskmanager.edit.EditTaskActivity
import id.ak.mytaskmanager.ui_common.navigation.IntentFactory
import id.ak.mytaskmanager.view.ViewTaskActivity
import javax.inject.Inject

class IntentGenerator @Inject constructor() : IntentFactory {
    override fun createTask(context: Context): Intent {
        return Intent(context, CreateTaskActivity::class.java)
    }

    override fun viewTask(context: Context, taskId: Int): Intent {
        return Intent(context, ViewTaskActivity::class.java).apply {
            putExtra(IntentFactory.Extras.TASK_ID, taskId)
        }
    }

    override fun editTask(context: Context, taskId: Int): Intent {
        return Intent(context, EditTaskActivity::class.java).apply {
            putExtra(IntentFactory.Extras.TASK_ID, taskId)
        }
    }
}