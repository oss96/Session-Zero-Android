package com.ossalali.sessionzero.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String = "Delete",
    dismissText: String = "Cancel",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ConfirmDialogPreview() {
    SessionZeroTheme(dynamicColor = false) {
        ConfirmDialog(
            title = "Delete Character",
            message = "Are you sure you want to delete Thorn Ironforge?",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
