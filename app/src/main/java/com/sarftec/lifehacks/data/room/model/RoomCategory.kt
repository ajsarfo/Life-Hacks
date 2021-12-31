package com.sarftec.lifehacks.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.lifehacks.data.room.ROOM_CATEGORY_TABLE

@Entity(tableName = ROOM_CATEGORY_TABLE)
class RoomCategory(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category: String
)