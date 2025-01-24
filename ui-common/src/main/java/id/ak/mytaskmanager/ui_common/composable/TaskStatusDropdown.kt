package id.ak.mytaskmanager.ui_common.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskStatusDropdown(
    text: String,
    items: List<String>,
    selectedIndex: Int?,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var showStatusOptions by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        TextButton(onClick = { showStatusOptions = true }, enabled = enabled) {
            Text(text = text)
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "change status"
            )
        }
        DropdownMenu(
            expanded = showStatusOptions,
            onDismissRequest = { showStatusOptions = false }
        ) {
            items.forEachIndexed { i, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onClick(i)
                        showStatusOptions = false
                    },
                    trailingIcon = {
                        if (i == selectedIndex) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "selected: $item"
                            )
                        }
                    }
                )
            }
        }
    }
}