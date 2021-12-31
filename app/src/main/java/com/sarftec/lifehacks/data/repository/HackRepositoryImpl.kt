package com.sarftec.lifehacks.data.repository

import com.sarftec.lifehacks.data.room.HackDatabase
import com.sarftec.lifehacks.data.room.toDomain
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.utils.Resource
import javax.inject.Inject

class HackRepositoryImpl @Inject constructor(
    private val hackDatabase: HackDatabase
) : HackRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        return Resource.success(
            hackDatabase.roomCategoryDao().getCategories().map {
                it.toDomain()
            }
        )
    }

    override suspend fun getHacksForCategory(categoryId: Int): Resource<List<Hack>> {
        return Resource.success(
            hackDatabase.roomHackDao().getHacksForCategory(categoryId).map {
                it.toDomain()
            }
        )
    }
}