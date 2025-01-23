package id.ak.mytaskmanager.ui_common.extension

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun ComponentActivity.checkPermission(
    permissions: Iterable<String>,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    onHasBeenGranted: (List<String>) -> Unit,
    onHasBeenDenied: (List<String>) -> Unit
) {
    val deniedPermission = mutableListOf<String>()
    val grantedPermission = mutableListOf<String>()
    val permissionToRequest = mutableListOf<String>().apply { addAll(permissions) }

    permissions.forEach {
        when {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED -> {
                grantedPermission.add(it)
                permissionToRequest.remove(it)
            }

            shouldShowRequestPermissionRationale(it) -> {
                deniedPermission.add(it)
                permissionToRequest.remove(it)
            }
        }
    }

    if (grantedPermission.isNotEmpty()) {
        onHasBeenGranted(grantedPermission)
    }

    if (deniedPermission.isNotEmpty()) {
        onHasBeenDenied(deniedPermission)
    }

    if (permissionToRequest.isNotEmpty()) {
        permissionLauncher.launch(permissionToRequest.toTypedArray())
    }
}