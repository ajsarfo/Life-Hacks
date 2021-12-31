package com.sarftec.lifehacks.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sarftec.lifehacks.data.room.ROOM_HACK_TABLE
import com.sarftec.lifehacks.data.room.model.RoomHack

@Dao
interface RoomHackDao {

    @Insert
    suspend fun insertHacks(hacks: List<RoomHack>)

    @Query("select * from $ROOM_HACK_TABLE where categoryId = :categoryId")
    suspend fun getHacksForCategory(categoryId: Int) : List<RoomHack>

    @Query("select * from $ROOM_HACK_TABLE where isFavorite = 1")
    suspend fun getBookmarks() : List<RoomHack>

    @Query("update $ROOM_HACK_TABLE set isFavorite = 1 where id = :hackId")
    suspend fun bookmarkHack(hackId: Int)

    @Query("update $ROOM_HACK_TABLE set isFavorite = 0 where id = :hackId")
    suspend fun removeBookmarkHack(hackId: Int)
}