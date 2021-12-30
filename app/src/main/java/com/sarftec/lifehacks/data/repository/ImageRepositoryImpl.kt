package com.sarftec.lifehacks.data.repository

import android.net.Uri
import com.sarftec.lifehacks.data.local.image.LocalAssets
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.repository.ImageRepository
import com.sarftec.lifehacks.utils.Resource
import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(
    private val localAssets: LocalAssets
): ImageRepository {

    override suspend fun getRandomHackBackground(): Resource<Uri> {
       return localAssets.getRandomHackBackground()
    }

    override suspend fun getCategoryImage(category: Category): Resource<Uri> {
        return localAssets.getCategoryImage(category)
    }
}