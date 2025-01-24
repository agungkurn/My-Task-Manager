package id.ak.mytaskmanager.create

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.ak.mytaskmanager.ui_common.theme.AppTheme

@AndroidEntryPoint
class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                CreateTask(
                    close = { finish() },
                    onNavigateUp = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }
}