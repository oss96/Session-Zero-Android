package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.ClassDefinition
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.model.SkillName

object ClassData {

    fun forClass(name: ClassName): ClassDefinition = ALL_CLASSES.first { it.name == name }

    val ALL_CLASSES: List<ClassDefinition> = listOf(
        // Populated in Phase 3
    )
}
