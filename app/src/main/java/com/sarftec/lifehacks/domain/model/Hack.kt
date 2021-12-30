package com.sarftec.lifehacks.domain.model

data class Hack(
    val id: Int,
    val categoryId: Int,
    val message: String,
    var isFavorite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if(other !is Hack) return  false
        return other.id == this.id
    }
}