package com.sarftec.lifehacks.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.lifehacks.data.room.ROOM_HACK_TABLE

@Entity(tableName = ROOM_HACK_TABLE)
class RoomHack(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val categoryId: Int,
    val message: String,
    var isFavorite: Boolean = false
)