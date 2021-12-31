package com.sarftec.lifehacks.view.listerner

import com.sarftec.lifehacks.view.parcel.ListToDetail

interface BookmarkFragmentListener {
    fun navigateToDetail(parcel: ListToDetail)
}