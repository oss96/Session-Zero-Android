package com.ossalali.sessionzero.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.ossalali.sessionzero.data.local.entity.CharacterEntity
import com.ossalali.sessionzero.data.local.entity.EquipmentItemEntity
import com.ossalali.sessionzero.data.local.entity.WeaponEntity

data class CharacterWithDetails(
    @Embedded val character: CharacterEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId",
    )
    val equipment: List<EquipmentItemEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId",
    )
    val weapons: List<WeaponEntity>,
)
