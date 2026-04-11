package com.ossalali.sessionzero.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.ossalali.sessionzero.data.local.entity.CharacterEntity
import com.ossalali.sessionzero.data.local.entity.EquipmentItemEntity
import com.ossalali.sessionzero.data.local.entity.WeaponEntity
import com.ossalali.sessionzero.data.local.relation.CharacterWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Transaction
    @Query("SELECT * FROM characters ORDER BY updatedAt DESC")
    fun getAllCharactersWithDetails(): Flow<List<CharacterWithDetails>>

    @Transaction
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterWithDetails(id: String): Flow<CharacterWithDetails?>

    @Upsert
    suspend fun upsertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipmentItems(items: List<EquipmentItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeapons(weapons: List<WeaponEntity>)

    @Query("DELETE FROM equipment_items WHERE characterId = :characterId")
    suspend fun deleteEquipmentForCharacter(characterId: String)

    @Query("DELETE FROM weapons WHERE characterId = :characterId")
    suspend fun deleteWeaponsForCharacter(characterId: String)

    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteCharacter(id: String)

    @Transaction
    suspend fun saveCharacterFull(
        character: CharacterEntity,
        equipment: List<EquipmentItemEntity>,
        weapons: List<WeaponEntity>,
    ) {
        upsertCharacter(character)
        deleteEquipmentForCharacter(character.id)
        deleteWeaponsForCharacter(character.id)
        insertEquipmentItems(equipment)
        insertWeapons(weapons)
    }
}
