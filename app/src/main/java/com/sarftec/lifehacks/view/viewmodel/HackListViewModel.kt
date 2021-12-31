package com.sarftec.lifehacks.view.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.view.parcel.CategoryToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HackListViewModel @Inject constructor(
    private val repository: HackRepository,
    private val stateHandle: SavedStateHandle
) : BaseHackListViewModel() {

    fun getCategory() : String? {
        return stateHandle.get<CategoryToList>(PARCEL)?.category
    }

    fun fetchHacks() {
        _screenState.value = ScreenState.Loading
        val categoryId = stateHandle.get<CategoryToList>(PARCEL)?.categoryId ?: let {
            _screenState.value = ScreenState.Error("Error => Parcel not FOUND!!")
            return
        }
        viewModelScope.launch {
            repository.getHacksForCategory(categoryId).let {
                _screenState.value = if(it.isSuccess()) ScreenState.Result(
                    it.data!!.shuffled(Random(getRandomGeneratorId()))
                )
                else ScreenState.Error(it.message!!)
            }
        }
    }

    fun setParcel(parcel: CategoryToList) {
        stateHandle.set(PARCEL, parcel)
    }

    fun getRandomGeneratorId() : Int {
        return stateHandle.get<Int>(RANDOM_ID) ?: (0 until 100).random().also {
            stateHandle.set(RANDOM_ID, it)
        }
    }

    companion object {
        const val PARCEL = "parcel"
        const val RANDOM_ID = "random_id"
    }
}