package com.sarftec.lifehacks.domain.repository

import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.utils.Resource

interface BookmarkRepository {
    suspend fun getBookmarks() : Resource<List<Hack>>
    suspend fun addBookmark(hack: Hack)
    suspend fun removeBookmark(hackId: Int)
}