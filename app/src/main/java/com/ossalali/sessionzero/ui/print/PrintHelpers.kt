package com.ossalali.sessionzero.ui.print

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Weapon

private val borderColor = Color(0xFFCCCCCC)
private val innerDividerColor = Color(0xFFDDDDDD)
private val sectionShape = RoundedCornerShape(size = 3.dp)

fun Modifier.printSectionBorder(): Modifier =
    this.border(width = 1.dp, color = borderColor, shape = sectionShape)

@Composable
fun PrintDivider() {
    HorizontalDivider(color = innerDividerColor, thickness = 0.5.dp)
}

@Composable
fun PrintLabelValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun DeathSaveRow(
    label: String,
    count: Int = 3,
    borderColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
        )
        repeat(count) {
            Box(
                modifier = Modifier
                    .size(size = 10.dp)
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(size = 2.dp)),
            )
        }
    }
}

@Composable
fun SpellSlotRow(slotCount: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(space = 2.dp)) {
        repeat(slotCount) {
            Box(
                modifier = Modifier
                    .size(size = 8.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF666666),
                        shape = RoundedCornerShape(size = 1.dp),
                    ),
            )
        }
    }
}

@Composable
fun PrintAttacksTable(
    weapons: List<Weapon>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.printSectionBorder()) {
        Text(
            text = "ATTACKS & SPELLCASTING",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
        )
        PrintDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 1.dp),
        ) {
            Text(text = "NAME", style = MaterialTheme.typography.labelSmall, modifier = Modifier.weight(weight = 2f))
            Text(text = "ATK", style = MaterialTheme.typography.labelSmall, modifier = Modifier.weight(weight = 1f))
            Text(text = "DAMAGE/TYPE", style = MaterialTheme.typography.labelSmall, modifier = Modifier.weight(weight = 2f))
        }
        PrintDivider()

        val rows = weapons.take(n = 3)
        rows.forEach { weapon ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
            ) {
                Text(text = weapon.name, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(weight = 2f))
                Text(text = weapon.attackBonus, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(weight = 1f))
                Text(text = weapon.damage, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(weight = 2f))
            }
            PrintDivider()
        }

        // Pad to 3 rows
        repeat(3 - rows.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 16.dp),
            )
            if (it < 2 - rows.size) PrintDivider()
        }
    }
}
