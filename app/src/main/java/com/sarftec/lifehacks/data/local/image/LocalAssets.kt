package com.sarftec.lifehacks.data.local.image

import android.content.Context
import android.net.Uri
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAssets @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val hackBackgrounds by lazy {
        context.assets.list(HACK_BACKGROUNDS)
    }

    private val categoryImages by lazy {
        context.assets.list(CATEGORY_IMAGES)
    }

    fun getRandomHackBackground() : Resource<Uri> {
        return try {
            Resource.success(
                hackBackgrounds!!.random().toAssetUri(HACK_BACKGROUNDS)
            )
        } catch (e: Exception) {
            Resource.error("Error => ${e.message}")
        }
    }

    fun getCategoryImage(category: Category) : Resource<Uri> {
        return try {
          Resource.success(
              categoryImages!!.first { it.substringBefore("_") == category.id.toString()}
                  .toAssetUri(CATEGORY_IMAGES)
          )
        } catch (e: Exception) {
            Resource.error("Error => ${e.message}")
        }
    }

    fun getLogo() : Uri = "logo.jpg".toAssetUri("logo")

    private fun String.toAssetUri(folder: String) : Uri {
        return Uri.parse("file:///android_asset/$folder/$this")
    }

    companion object {
        const val HACK_BACKGROUNDS = "hack_backgrounds"
        const val CATEGORY_IMAGES = "category_images"
    }
}