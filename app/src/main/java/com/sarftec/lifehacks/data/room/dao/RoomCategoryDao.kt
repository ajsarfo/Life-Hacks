package com.sarftec.lifehacks.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.lifehacks.data.room.ROOM_CATEGORY_TABLE
import com.sarftec.lifehacks.data.room.model.RoomCategory

@Dao
interface RoomCategoryDao {

    @Query("select * from $ROOM_CATEGORY_TABLE")
    suspend fun getCategories() : List<RoomCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<RoomCategory>)
}