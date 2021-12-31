package com.sarftec.lifehacks.data.repository

import com.sarftec.lifehacks.data.room.HackDatabase
import com.sarftec.lifehacks.data.room.toDomain
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.BookmarkRepository
import com.sarftec.lifehacks.utils.Resource
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val hackDatabase: HackDatabase
): BookmarkRepository {
    override suspend fun getBookmarks(): Resource<List<Hack>> {
        return Resource.success(
            hackDatabase.roomHackDao().getBookmarks().map { it.toDomain() }
        )
    }

    override suspend fun addBookmark(hack: Hack) {
        hackDatabase.roomHackDao().bookmarkHack(hack.id)
    }

    override suspend fun removeBookmark(hackId: Int) {
        hackDatabase.roomHackDao().removeBookmarkHack(hackId)
    }
}