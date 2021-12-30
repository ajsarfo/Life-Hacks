package com.sarftec.lifehacks.domain.repository

import android.net.Uri
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.utils.Resource

interface ImageRepository {
    suspend fun getRandomHackBackground() : Resource<Uri>
    suspend fun getCategoryImage(category: Category) : Resource<Uri>
}