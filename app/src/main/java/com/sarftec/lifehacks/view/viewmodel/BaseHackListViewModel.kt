package com.sarftec.lifehacks.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarftec.lifehacks.domain.model.Hack

abstract class BaseHackListViewModel : ViewModel() {

    protected val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    sealed class ScreenState() {
        object Loading : ScreenState()
        class Result(val hacks: List<Hack>) : ScreenState()
        class Error(val message: String) : ScreenState()
    }

    abstract fun bookmark(hack: Hack)

    abstract fun handleRebind()
}