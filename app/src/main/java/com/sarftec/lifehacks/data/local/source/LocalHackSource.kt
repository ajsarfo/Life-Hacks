package com.sarftec.lifehacks.data.local.source

import android.content.Context
import com.sarftec.lifehacks.data.local.source.json.JsonCategory
import com.sarftec.lifehacks.data.local.source.json.JsonHack
import com.sarftec.lifehacks.data.local.source.json.toDomain
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalHackSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun getHacks(): Resource<List<Hack>> {
        return with(Dispatchers.IO) {
            retrieveData<JsonHack, Hack> (HACKS_FOLDER) {
                it.toDomain()
            }
        }
    }

    suspend fun getCategories(): Resource<List<Category>> {
      return with(Dispatchers.IO) {
          retrieveData<JsonCategory, Category> (CATEGORIES_FOLDER) {
              it.toDomain()
          }
      }
    }

    private inline fun <reified T, R> retrieveData(
        folder: String,
        mapper: (T) -> R
    ): Resource<List<R>> {
        return try {
            val data: List<T> = context.assets.open(folder)
                .bufferedReader()
                .use { Json.decodeFromString(it.readText()) }
            Resource.success(
               data.map { mapper(it) }
            )
        } catch (e: Exception) {
            Resource.error("Error => ${e.message}")
        }
    }

    companion object {
        const val HACKS_FOLDER = "hacks/contents.json"
        const val CATEGORIES_FOLDER = "hacks/categories.json"
    }
}