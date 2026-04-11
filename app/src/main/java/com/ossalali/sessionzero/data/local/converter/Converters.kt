package com.ossalali.sessionzero.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(value: List<String>): String = json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isBlank() || value == "[]") emptyList()
        else json.decodeFromString(value)

    @TypeConverter
    fun fromStringIntMap(value: Map<String, Int>): String = json.encodeToString(value)

    @TypeConverter
    fun toStringIntMap(value: String): Map<String, Int> =
        if (value.isBlank() || value == "{}") emptyMap()
        else json.decodeFromString(value)
}
