package com.ossalali.sessionzero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ossalali.sessionzero.data.local.converter.Converters
import com.ossalali.sessionzero.data.local.dao.CharacterDao
import com.ossalali.sessionzero.data.local.entity.CharacterEntity
import com.ossalali.sessionzero.data.local.entity.EquipmentItemEntity
import com.ossalali.sessionzero.data.local.entity.WeaponEntity

@Database(
    entities = [
        CharacterEntity::class,
        EquipmentItemEntity::class,
        WeaponEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
