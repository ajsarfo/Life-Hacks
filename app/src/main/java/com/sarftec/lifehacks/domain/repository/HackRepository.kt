package com.sarftec.lifehacks.domain.repository

import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.utils.Resource

interface HackRepository {
    suspend fun getCategories() : Resource<List<Category>>
    suspend fun getHacksForCategory(categoryId: Int) : Resource<List<Hack>>
}