package com.sarftec.lifehacks.view.parcel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ListToDetail(
    val categoryId: Int,
    val category: String,
    var position: Int,
    val randomId: Int,
    val origin: Int
) : Parcelable {
    companion object {
        const val FROM_LIST = 0
        const val FROM_BOOKMARKS = 1
    }
}