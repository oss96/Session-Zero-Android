package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.ui.common.CircleStat
import com.ossalali.sessionzero.ui.common.HeartStat
import com.ossalali.sessionzero.ui.common.HexagonShape
import com.ossalali.sessionzero.ui.common.HexagonStat
import com.ossalali.sessionzero.ui.common.RectangleStat
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.common.ShieldStat
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun CombatSection(derivedStats: DerivedStats) {
    SectionHeader(text = "Combat")
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ShieldStat(
                label = "AC",
                value = "${derivedStats.armorClass}",
            )
            HeartStat(label = "HP", value = "${derivedStats.maxHP}")
        }

        Spacer(modifier = Modifier.height(height = 8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            RectangleStat(label = "Init", value = "+${derivedStats.initiative}")
            RectangleStat(label = "Speed", value = "${derivedStats.speed}ft")
            HexagonStat(label = "Hit Dice", value = derivedStats.hitDice)
            CircleStat(label = "Prof", value = "+${derivedStats.proficiencyBonus}")
        }
    }

    Spacer(modifier = Modifier.height(height = 16.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PassiveScoreCard(value = derivedStats.passivePerception, label = "Passive Perception")
        Spacer(modifier = Modifier.height(height = 4.dp))
        PassiveScoreCard(value = derivedStats.passiveInvestigation, label = "Passive Investigation")
        Spacer(modifier = Modifier.height(height = 4.dp))
        PassiveScoreCard(value = derivedStats.passiveInsight, label = "Passive Insight")
    }
}

@Composable
private fun PassiveScoreCard(value: Int, label: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(size = 16.dp),
                color = MaterialTheme.colorScheme.secondary,
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(size = 16.dp),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(width = 56.dp, height = 48.dp)
                    .padding(end = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = HexagonShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = value.toString(),
                    textAlign = TextAlign.Center,
                )
            }
            Text(text = label)
        }
    }
}

@PreviewLightDark
@Composable
private fun CombatSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            CombatSection(derivedStats = PreviewData.sampleDerivedStats)
        }
    }
}
