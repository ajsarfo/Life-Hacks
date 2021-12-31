package com.sarftec.lifehacks.data.room

import com.sarftec.lifehacks.data.room.model.RoomCategory
import com.sarftec.lifehacks.data.room.model.RoomHack
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.model.Hack

fun RoomHack.toDomain() : Hack {
    return Hack(id, categoryId, message, isFavorite)
}

fun Hack.toRoom() : RoomHack {
    return RoomHack(id, categoryId, message, isFavorite)
}

fun RoomCategory.toDomain() : Category {
    return Category(id, category)
}

fun Category.toRoom() : RoomCategory {
    return RoomCategory(id, category)
}