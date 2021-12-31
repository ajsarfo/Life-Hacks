package com.sarftec.lifehacks.view.viewmodel

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.*
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.domain.repository.ImageRepository
import com.sarftec.lifehacks.utils.Event
import com.sarftec.lifehacks.view.parcel.ListToDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val hackRepository: HackRepository,
    private val imageRepository: ImageRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    private val _background = MutableLiveData<Event<Uri>>()
    val background: LiveData<Event<Uri>>
        get() = _background

    fun fetchHacks() {
        val parcel = stateHandle.get<DetailParcel>(PARCEL) ?: let {
            _screenState.value = ScreenState.Error("Error => parcel not FOUND!")
            return
        }
        viewModelScope.launch {
            setScreenState(parcel.parcel)
            getBackground()?.let { _background.value = Event(it) }
                ?: generateRandomBackground()
        }
    }

    fun bookmarkHack(hack: Hack) {

    }

    fun setCurrentPosition(position: Int) {
        stateHandle.get<DetailParcel>(PARCEL)?.let {
            it.parcel.position = position
        }
    }

    fun getCurrentHack() : Hack? {
        return stateHandle.get<DetailParcel>(PARCEL)?.let {
            getHackAtPosition(it.parcel.position)
        }
    }

    fun getHackAtPosition(position: Int) : Hack? {
        val screenState = _screenState.value ?: return null
        if(screenState !is ScreenState.Result) return null
        return screenState.hacks[position]
    }

    private suspend fun setScreenState(parcel: ListToDetail) {
        when (parcel.origin) {
            ListToDetail.FROM_LIST -> hackRepository.getHacksForCategory(parcel.categoryId).let {
                _screenState.value = if (it.isSuccess()) ScreenState.Result(
                    it.data!!.shuffled(Random(parcel.randomId)),
                    parcel.position
                )
                else ScreenState.Error(it.message!!)
            }
            else -> {

            }
        }
    }

    fun generateRandomBackground() {
        viewModelScope.launch {
            imageRepository.getRandomHackBackground().let {
                if (it.isSuccess()) it.data!!.also { uri ->
                    setBackground(uri)
                    _background.value = Event(uri)
                }
            }
        }
    }

    private fun setBackground(uri: Uri) {
        stateHandle.get<DetailParcel>(PARCEL)?.let {
            it.background = uri
        }
    }

    fun getBackground(): Uri? {
        return stateHandle.get<DetailParcel>(PARCEL)?.background
    }

    fun setParcel(parcel: ListToDetail) {
        stateHandle.set(PARCEL, DetailParcel(parcel))
    }

    companion object {
        const val PARCEL = "parcel"
    }

    sealed class ScreenState() {
        object Loading : ScreenState()
        class Result(val hacks: List<Hack>, val position: Int) : ScreenState()
        class Error(val message: String) : ScreenState()
    }

    @Parcelize
    class DetailParcel(
        val parcel: ListToDetail,
        var background: Uri? = null
    ) : Parcelable
}