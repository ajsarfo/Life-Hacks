package com.sarftec.lifehacks.view.parcel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryToList(
    val category: String,
    val categoryId: Int
) : Parcelable