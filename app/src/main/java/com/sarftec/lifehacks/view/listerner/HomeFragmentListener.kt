package com.sarftec.lifehacks.view.listerner

import com.sarftec.lifehacks.view.parcel.CategoryToList

interface HomeFragmentListener {
    fun navigateToList(parcel: CategoryToList)
}