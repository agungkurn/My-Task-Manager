package id.ak.mytaskmanager.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.ak.mytaskmanager.ui_common.navigation.IntentFactory
import id.ak.mytaskmanager.ui_common.theme.AppTheme

@AndroidEntryPoint
class ViewTaskActivity : ComponentActivity() {
    private val taskId by lazy {
        intent.getIntExtra(IntentFactory.Extras.TASK_ID, -1).takeIf { it > -1 }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                taskId?.let { id ->
                    ViewTask(
                        taskId = id,
                        onNavigateUp = onBackPressedDispatcher::onBackPressed,
                        onEdit = {}
                    )
                }
            }
        }
    }
}