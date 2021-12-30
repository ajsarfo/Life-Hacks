package com.sarftec.lifehacks.view.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.domain.repository.ImageRepository
import com.sarftec.lifehacks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HackRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState>
    get() = _screenState

    fun fetchCategories() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {
            _screenState.value = repository.getCategories().let {
                if(it.isSuccess()) ScreenState.Result(it.data!!)
                else ScreenState.Error(it.message!!)
            }
        }
    }

    suspend fun getImage(category: Category) : Resource<Uri> {
        return imageRepository.getCategoryImage(category)
    }

    sealed class ScreenState() {
        object Loading : ScreenState()
        class Result(val categories: List<Category>) : ScreenState()
        class Error(val message: String) : ScreenState()
    }
}