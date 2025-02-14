package id.ak.mytaskmanager.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.ak.mytaskmanager.ui_common.navigation.IntentFactory
import id.ak.mytaskmanager.ui_common.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @Inject
    lateinit var intentFactory: IntentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Home(
                    openCreateTask = {
                        startActivity(intentFactory.createTask(this))
                    },
                    openTaskDetails = {
                        startActivity(intentFactory.viewTask(this, it.id))
                    }
                )
            }
        }
    }
}