package com.project.dailyquest.utils

import androidx.room.TypeConverter
import java.util.UUID

class UuidConverter {
    @TypeConverter fun getStringFrom(uuid: UUID): String = uuid.toString()
    @TypeConverter fun getUuidFrom(string: String): UUID = UUID.fromString(string)
}