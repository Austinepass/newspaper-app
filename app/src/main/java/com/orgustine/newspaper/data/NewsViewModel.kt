package com.orgustine.newspaper.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orgustine.newspaper.data.model.News
import com.orgustine.newspaper.network.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsViewModel (private val newsRepository: NewsRepository) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<News>>()
    val dataState: MutableLiveData<DataState<News>>
        get() = _dataState


    fun getNews( section : String,
                 timePeriod : Int) {
        viewModelScope.launch {
            newsRepository.getAllNews(section, timePeriod)
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }
}