package com.sarftec.lifehacks.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.BookmarkRepository
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.utils.Event
import com.sarftec.lifehacks.view.file.rebindHacks
import com.sarftec.lifehacks.view.parcel.CategoryToList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HackListViewModel @Inject constructor(
    private val repository: HackRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val stateHandle: SavedStateHandle
) : BaseHackListViewModel() {

    private val _rebindList = MutableLiveData<Event<Collection<Hack>>>()
    val rebindList: LiveData<Event<Collection<Hack>>>
        get() = _rebindList

    fun getCategory(): String? {
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
                _screenState.value = if (it.isSuccess()) ScreenState.Result(
                    it.data!!.shuffled(Random(getRandomGeneratorId()))
                )
                else ScreenState.Error(it.message!!)
            }
        }
    }

    override fun bookmark(hack: Hack) {
        viewModelScope.launch {
            if (hack.isFavorite) bookmarkRepository.addBookmark(hack)
            else bookmarkRepository.removeBookmark(hack.id)
        }
    }

    override fun handleRebind() {
        _rebindList.value = Event(rebindHacks.toList())
        _screenState.value?.let {
            if (it is ScreenState.Result) rebindHacks.forEach { hack ->
                it.hacks.forEach { screenHack ->
                    if (screenHack == hack) screenHack.isFavorite = hack.isFavorite
                }
            }
        }
        rebindHacks.clear()
    }

    fun setParcel(parcel: CategoryToList) {
        stateHandle.set(PARCEL, parcel)
    }

    fun getRandomGeneratorId(): Int {
        return stateHandle.get<Int>(RANDOM_ID) ?: (0 until 100).random().also {
            stateHandle.set(RANDOM_ID, it)
        }
    }

    companion object {
        const val PARCEL = "parcel"
        const val RANDOM_ID = "random_id"
    }
}