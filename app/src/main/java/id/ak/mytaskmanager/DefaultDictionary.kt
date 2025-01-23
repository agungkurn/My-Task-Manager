package id.ak.mytaskmanager

import id.ak.mytaskmanager.create.CreateTaskActivity
import id.ak.mytaskmanager.ui_common.navigation.ActivityDictionary
import javax.inject.Inject

class DefaultDictionary @Inject constructor() : ActivityDictionary {
    override val createTask = CreateTaskActivity::class.java
}