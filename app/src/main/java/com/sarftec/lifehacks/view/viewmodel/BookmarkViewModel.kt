package com.sarftec.lifehacks.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.BookmarkRepository
import com.sarftec.lifehacks.utils.Event
import com.sarftec.lifehacks.view.file.rebindHacks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: BookmarkRepository
): BaseHackListViewModel() {

    private var items = mutableListOf<Hack>()

    private val _itemRemoved = MutableLiveData<Event<Int>>()
    val itemRemoved: LiveData<Event<Int>>
    get() = _itemRemoved

    override fun bookmark(hack: Hack) {
        val index = items.indexOf(hack)
        if(index == -1) return
        items.removeAt(index)
        _itemRemoved.value = Event(index)
      viewModelScope.launch {
          repository.removeBookmark(hack.id)
      }
    }

    fun fetchHacks() {
        _screenState.value = ScreenState.Loading
        if(items.isNotEmpty()) {
            _screenState.value = ScreenState.Result(items)
            return
        }
        viewModelScope.launch {
            _screenState.value = repository.getBookmarks().let {
                if(it.isSuccess()) ScreenState.Result(
                    it.data!!.also { content  -> items = content.toMutableList() }
                )
                else ScreenState.Error(it.message!!)
            }
        }
    }

    override fun handleRebind() {
        if(rebindHacks.isEmpty()) return
        if(rebindHacks.size == 1) {
            val index = items.indexOf(rebindHacks.first())
            if(index == -1) return
            items.removeAt(index)
            _itemRemoved.value = Event(index)
            rebindHacks.clear()
            return
        }
        else {
            rebindHacks.forEach { items.remove(it) }
            _screenState.value = ScreenState.Result(items)
            rebindHacks.clear()
        }
    }
}