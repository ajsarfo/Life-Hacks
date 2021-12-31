package com.sarftec.lifehacks.data.injection

import android.content.Context
import com.sarftec.lifehacks.data.room.HackDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun hackDatabase(@ApplicationContext context: Context) : HackDatabase {
        return HackDatabase.getInstance(context)
    }
}