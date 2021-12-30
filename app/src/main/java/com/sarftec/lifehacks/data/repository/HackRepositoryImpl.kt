package com.sarftec.lifehacks.data.repository

import com.sarftec.lifehacks.data.local.source.LocalSource
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.utils.Resource
import javax.inject.Inject

class HackRepositoryImpl @Inject constructor(
    private val localSource: LocalSource
) : HackRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        return localSource.getCategories()
    }

    override suspend fun getHacksForCategory(categoryId: Int): Resource<List<Hack>> {
        return localSource.getHacks()
    }
}