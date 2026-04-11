package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.BackgroundDefinition
import com.ossalali.sessionzero.domain.model.BackgroundName

object BackgroundData {

    fun forBackground(name: BackgroundName): BackgroundDefinition =
        ALL_BACKGROUNDS.first { it.name == name }

    val ALL_BACKGROUNDS: List<BackgroundDefinition> = listOf(
        // Populated in Phase 3
    )
}
