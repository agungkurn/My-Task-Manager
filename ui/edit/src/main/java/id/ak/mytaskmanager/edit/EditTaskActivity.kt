package id.ak.mytaskmanager.edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.ak.mytaskmanager.ui_common.navigation.IntentFactory
import id.ak.mytaskmanager.ui_common.theme.AppTheme

@AndroidEntryPoint
class EditTaskActivity : ComponentActivity() {
    private val taskId by lazy {
        intent.getIntExtra(IntentFactory.Extras.TASK_ID, -1).takeIf { it > -1 }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                taskId?.let { id ->
                    EditTask(
                        taskId = id,
                        onSaved = { finish() },
                        onNavigateUp = { onBackPressedDispatcher.onBackPressed() }
                    )
                }
            }
        }
    }
}