package id.ak.mytaskmanager.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.ak.mytaskmanager.ui_common.navigation.ActivityDictionary
import id.ak.mytaskmanager.ui_common.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @Inject
    lateinit var activityDictionary: ActivityDictionary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Home(
                    openCreateTask = {
                        Intent(this, activityDictionary.createTask).also {
                            startActivity(it)
                        }
                    },
                    openTaskDetails = {}
                )
            }
        }
    }
}