package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun <T> SelectionGrid(
    items: List<T>,
    selectedItem: T?,
    onSelect: (T) -> Unit,
    label: (T) -> String,
    description: ((T) -> String)? = null,
    modifier: Modifier = Modifier,
    columns: Int = 2,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowItems.forEach { item ->
                    DndCard(
                        selected = item == selectedItem,
                        onClick = { onSelect(item) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Column {
                            Text(
                                text = label(item),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            if (description != null) {
                                Text(
                                    text = description(item),
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 2,
                                )
                            }
                        }
                    }
                }
                // Fill remaining space if row is not full
                repeat(columns - rowItems.size) {
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectionGridPreview() {
    val items = listOf("Fighter", "Wizard", "Rogue", "Cleric", "Ranger")
    SessionZeroTheme(dynamicColor = false) {
        SelectionGrid(
            items = items,
            selectedItem = "Wizard",
            onSelect = {},
            label = { it },
            description = { "A $it class" },
            modifier = Modifier.padding(16.dp),
        )
    }
}
