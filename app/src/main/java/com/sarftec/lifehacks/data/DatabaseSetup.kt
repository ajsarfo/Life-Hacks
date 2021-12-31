package com.sarftec.lifehacks.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.sarftec.lifehacks.data.local.source.LocalHackSource
import com.sarftec.lifehacks.data.room.HackDatabase
import com.sarftec.lifehacks.data.room.toRoom
import com.sarftec.lifehacks.utils.editSettings
import com.sarftec.lifehacks.utils.readSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DatabaseSetup @Inject constructor(
    @ApplicationContext private val context: Context,
    private val hackDatabase: HackDatabase,
    private val localHackSource: LocalHackSource
) {

    suspend fun setupDatabase() {
        if(context.readSettings(isSetup, false).first()) return
        localHackSource.getHacks().let { resource ->
            if(resource.isSuccess()) hackDatabase.roomHackDao().insertHacks(
                resource.data!!.map { it.toRoom() }
            )
        }
        localHackSource.getCategories().let { resource ->
            if(resource.isSuccess()) hackDatabase.roomCategoryDao().insertCategories(
                resource.data!!.map { it.toRoom() }
            )
        }
        context.editSettings(isSetup, true)
    }

    companion object {
        private val isSetup = booleanPreferencesKey("is_database_setup")
    }
}