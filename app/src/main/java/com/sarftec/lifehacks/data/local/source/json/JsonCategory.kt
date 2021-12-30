package com.sarftec.lifehacks.data.local.source.json

import kotlinx.serialization.Serializable

@Serializable
data class JsonCategory(
    val category_name: String,
    val id: Int
)