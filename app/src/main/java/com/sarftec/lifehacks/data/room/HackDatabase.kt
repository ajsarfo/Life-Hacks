package com.sarftec.lifehacks.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarftec.lifehacks.data.room.dao.RoomCategoryDao
import com.sarftec.lifehacks.data.room.dao.RoomHackDao
import com.sarftec.lifehacks.data.room.model.RoomCategory
import com.sarftec.lifehacks.data.room.model.RoomHack

@Database(
    entities = [
        RoomHack::class,
        RoomCategory::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HackDatabase : RoomDatabase() {

    abstract fun roomHackDao() : RoomHackDao
    abstract fun roomCategoryDao() : RoomCategoryDao

    companion object {
        private var INSTANCE: HackDatabase? = null

        fun getInstance(context: Context) : HackDatabase {
           return INSTANCE ?: synchronized(this) {
               INSTANCE ?: createDatabase(context).also {
                   INSTANCE = it
               }
           }
        }

        private fun createDatabase(context: Context) : HackDatabase {
            return Room.databaseBuilder(
                context,
                HackDatabase::class.java,
                ROOM_DATABASE
            ).build()
        }
    }
}