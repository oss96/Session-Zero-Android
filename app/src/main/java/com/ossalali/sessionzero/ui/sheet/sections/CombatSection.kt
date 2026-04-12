package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.ui.common.CircleStat
import com.ossalali.sessionzero.ui.common.HeartStat
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

    Spacer(modifier = Modifier.height(height = 8.dp))

    Text(text = "Passive Perception: ${derivedStats.passivePerception}")
    Text(text = "Passive Investigation: ${derivedStats.passiveInvestigation}")
    Text(text = "Passive Insight: ${derivedStats.passiveInsight}")
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
