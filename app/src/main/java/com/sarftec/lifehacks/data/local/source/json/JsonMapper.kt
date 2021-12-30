package com.sarftec.lifehacks.data.local.source.json

import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.model.Hack

fun JsonHack.toDomain() : Hack {
    return Hack(id, category_id, content)
}

fun JsonCategory.toDomain() : Category {
    return Category(id, category_name)
}