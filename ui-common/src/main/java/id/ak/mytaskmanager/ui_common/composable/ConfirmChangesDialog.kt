package id.ak.mytaskmanager.ui_common.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import id.ak.mytaskmanager.ui_common.R

@Composable
fun ConfirmChangesDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onDeny: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.action_yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDeny) {
                Text(text = stringResource(R.string.action_no))
            }
        },
        text = {
            Text(text = stringResource(R.string.confirm_changes))
        }
    )
}