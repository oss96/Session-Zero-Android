package com.ossalali.sessionzero.ui.print

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassDefinition
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.preview.PreviewData

private val borderColor = Color(0xFFCCCCCC)
private val innerDivider = Color(0xFFDDDDDD)
private val labelColor = Color(0xFF555555)
private val sectionShape = RoundedCornerShape(size = 3.dp)

@Composable
fun PrintPageOne(
    character: Character,
    derivedStats: DerivedStats,
    classDef: ClassDefinition?,
) {
    PrintTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
        ) {
            Column(modifier = Modifier.padding(all = 12.dp)) {
                // ── Header Bar ──
                PrintHeaderBar(character = character)

                Spacer(modifier = Modifier.height(height = 6.dp))

                // ── 3-Column Body ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
                ) {
                    // Left: Abilities + Skills
                    Column(modifier = Modifier.weight(weight = 0.28f)) {
                        AbilityColumn(
                            character = character,
                            derivedStats = derivedStats,
                            classDef = classDef,
                        )
                    }

                    // Center: Combat
                    Column(modifier = Modifier.weight(weight = 0.34f)) {
                        CombatColumn(
                            character = character,
                            derivedStats = derivedStats,
                        )
                    }

                    // Right: Proficiencies + Equipment
                    Column(modifier = Modifier.weight(weight = 0.34f)) {
                        RightColumn(
                            character = character,
                            derivedStats = derivedStats,
                            classDef = classDef,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PrintHeaderBar(character: Character) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = borderColor, shape = sectionShape),
    ) {
        // Name cell (2.5x width)
        HeaderCell(
            label = "CHARACTER NAME",
            value = character.name.ifEmpty { "" },
            modifier = Modifier.weight(weight = 2.5f),
        )
        // Other cells
        HeaderCell(
            label = "CLASS & LEVEL",
            value = "${character.className?.displayName ?: ""} ${character.level}",
            modifier = Modifier.weight(weight = 1f),
        )
        HeaderCell(
            label = "SPECIES",
            value = character.species?.displayName ?: "",
            modifier = Modifier.weight(weight = 1f),
        )
        HeaderCell(
            label = "BACKGROUND",
            value = character.background?.displayName ?: "",
            modifier = Modifier.weight(weight = 1f),
        )
        HeaderCell(
            label = "ALIGNMENT",
            value = character.alignment?.displayName ?: "",
            modifier = Modifier.weight(weight = 1f),
        )
        HeaderCell(
            label = "XP",
            value = "${character.xp}",
            modifier = Modifier.weight(weight = 0.7f),
        )
    }
}

@Composable
private fun HeaderCell(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(width = 0.5.dp, color = innerDivider)
            .padding(horizontal = 4.dp, vertical = 3.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 4.sp),
            color = labelColor,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
        )
    }
}

// ── Left Column: Ability Scores + Skills ──

@Composable
private fun AbilityColumn(
    character: Character,
    derivedStats: DerivedStats,
    classDef: ClassDefinition?,
) {
    val scores = character.totalAbilityScores
    val proficientSaves = classDef?.savingThrows ?: emptyList()

    AbilityName.entries.forEach { ability ->
        val score = scores[ability]
        val mod = GameRules.abilityModifier(score)
        val sign = if (mod >= 0) "+" else ""
        val saveBonus = derivedStats.savingThrows[ability] ?: 0
        val saveProficient = ability in proficientSaves
        val saveSign = if (saveBonus >= 0) "+" else ""
        val skills = AbilityScores.SKILLS_BY_ABILITY[ability] ?: emptyList()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp)
                .printSectionBorder()
                .padding(all = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Ability name
            Text(
                text = ability.name,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            // Score
            Text(
                text = "$score",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )

            // Modifier circle
            Box(
                modifier = Modifier
                    .size(size = 20.dp)
                    .border(width = 1.5.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .background(color = Color(0xFFF0F0F0), shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$sign$mod",
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(height = 2.dp))

            // Saving throw
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                ProfDot(filled = saveProficient)
                Spacer(modifier = Modifier.width(width = 3.dp))
                Text(text = "Saving Throw", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(width = 3.dp))
                Text(
                    text = "$saveSign$saveBonus",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            PrintDivider()

            // Skills
            skills.forEach { skill ->
                val skillBonus = derivedStats.skillBonuses[skill] ?: 0
                val skillProficient = skill in character.skillProficiencies
                val skillSign = if (skillBonus >= 0) "+" else ""

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.5.dp, horizontal = 2.dp),
                ) {
                    ProfDot(filled = skillProficient)
                    Spacer(modifier = Modifier.width(width = 2.dp))
                    Text(
                        text = skill.displayName,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(weight = 1f),
                    )
                    Text(
                        text = "$skillSign$skillBonus",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfDot(filled: Boolean) {
    Box(
        modifier = Modifier
            .size(size = 6.dp)
            .then(
                if (filled) {
                    Modifier.background(color = Color(0xFF333333), shape = CircleShape)
                } else {
                    Modifier.border(width = 1.dp, color = Color(0xFF666666), shape = CircleShape)
                }
            ),
    )
}

// ── Center Column: Combat Stats ──

@Composable
private fun CombatColumn(
    character: Character,
    derivedStats: DerivedStats,
) {
    // Proficiency Bonus
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = "PROFICIENCY BONUS", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.width(width = 8.dp))
        Text(
            text = "+${derivedStats.proficiencyBonus}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // AC / Initiative / Speed
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // AC
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 48.dp)
                    .printSectionBorder(),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${derivedStats.armorClass}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text(text = "AC", style = MaterialTheme.typography.labelSmall)
        }

        // Initiative
        PrintStatBox(label = "INITIATIVE", value = "+${derivedStats.initiative}")

        // Speed
        PrintStatBox(label = "SPEED", value = "${derivedStats.speed}ft")
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // HP Section
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder()
            .padding(all = 4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "HIT POINTS", style = MaterialTheme.typography.labelSmall)
            Text(
                text = "Max: ${derivedStats.maxHP}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.height(height = 2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(weight = 2f)
                    .height(height = 28.dp)
                    .border(width = 1.dp, color = borderColor, shape = sectionShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Current HP", style = MaterialTheme.typography.labelSmall)
            }
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .height(height = 28.dp)
                    .border(width = 1.dp, color = borderColor, shape = sectionShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Temp HP", style = MaterialTheme.typography.labelSmall)
            }
        }
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // Hit Dice
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder()
            .padding(vertical = 3.dp, horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "HIT DICE", style = MaterialTheme.typography.labelSmall)
        Text(text = derivedStats.hitDice, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // Death Saves
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder()
            .padding(all = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "DEATH SAVES", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(height = 2.dp))
        DeathSaveRow(label = "Successes", borderColor = Color(0xFF4CAF50))
        Spacer(modifier = Modifier.height(height = 2.dp))
        DeathSaveRow(label = "Failures", borderColor = Color(0xFFF44336))
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // Attacks
    PrintAttacksTable(weapons = character.weapons)
}

@Composable
private fun PrintStatBox(label: String, value: String) {
    Column(
        modifier = Modifier
            .printSectionBorder()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

// ── Right Column: Proficiencies + Equipment ──

@Composable
private fun RightColumn(
    character: Character,
    derivedStats: DerivedStats,
    classDef: ClassDefinition?,
) {
    // Passive Scores
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 3.dp),
    ) {
        PassiveBox(label = "Passive\nPerception", value = "${derivedStats.passivePerception}", modifier = Modifier.weight(weight = 1f))
        PassiveBox(label = "Passive\nInsight", value = "${derivedStats.passiveInsight}", modifier = Modifier.weight(weight = 1f))
        PassiveBox(label = "Passive\nInvestigation", value = "${derivedStats.passiveInvestigation}", modifier = Modifier.weight(weight = 1f))
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // Proficiencies & Languages
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder()
            .padding(all = 4.dp),
    ) {
        Text(
            text = "PROFICIENCIES & LANGUAGES",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(height = 3.dp))

        ProfCategory(label = "Armor", value = classDef?.armorProficiencies?.joinToString(separator = ", ") ?: "None")
        ProfCategory(label = "Weapons", value = classDef?.weaponProficiencies?.joinToString(separator = ", ") ?: "None")
        ProfCategory(label = "Tools", value = classDef?.toolProficiencies?.joinToString(separator = ", ") ?: "None")
        ProfCategory(label = "Languages", value = character.languages.joinToString(separator = ", ").ifEmpty { "Common" })
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // Equipment
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder()
            .padding(all = 4.dp),
    ) {
        Text(
            text = "EQUIPMENT",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(height = 2.dp))

        character.equipment.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = item.name, style = MaterialTheme.typography.bodySmall)
                if (item.quantity > 1) {
                    Text(text = "×${item.quantity}", style = MaterialTheme.typography.bodySmall, color = labelColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 4.dp))

        // Coins
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 3.dp),
        ) {
            CoinBox(label = "CP", value = "${character.coins.cp}", modifier = Modifier.weight(weight = 1f))
            CoinBox(label = "SP", value = "${character.coins.sp}", modifier = Modifier.weight(weight = 1f))
            CoinBox(label = "GP", value = "${character.coins.gp}", modifier = Modifier.weight(weight = 1f))
            CoinBox(label = "PP", value = "${character.coins.pp}", modifier = Modifier.weight(weight = 1f))
        }
    }
}

@Composable
private fun PassiveBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .printSectionBorder()
            .padding(all = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 4.sp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ProfCategory(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 2.dp)) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 4.sp),
            color = labelColor,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
        )
    }
}

@Composable
private fun CoinBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(width = 1.dp, color = borderColor, shape = sectionShape)
            .padding(vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 4.sp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Preview(widthDp = 595, heightDp = 842)
@Composable
private fun PrintPageOnePreview() {
    PrintPageOne(
        character = PreviewData.sampleCharacter,
        derivedStats = PreviewData.sampleDerivedStats,
        classDef = ClassData.ALL_CLASSES.find { it.name == PreviewData.sampleCharacter.className },
    )
}
