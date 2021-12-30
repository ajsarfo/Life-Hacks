package com.sarftec.lifehacks.data.local.source.json

import kotlinx.serialization.Serializable

@Serializable
data class JsonHack(
    val category_id: Int,
    val content: String,
    val favorite: Int,
    val id: Int
)