package com.sarftec.lifehacks.data.repository

import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.BookmarkRepository
import com.sarftec.lifehacks.utils.Resource
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(

): BookmarkRepository {
    override suspend fun getBookmarks(): Resource<List<Hack>> {
        return Resource.error("Error => not yet implemented!")
    }

    override suspend fun addBookmark(hack: Hack) {

    }

    override suspend fun removeBookmark(hackId: Int) {

    }
}