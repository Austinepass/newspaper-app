package com.orgustine.newspaper.data

import com.orgustine.newspaper.data.model.News
import com.orgustine.newspaper.network.RetrofitBuilder.API_KEY
import com.orgustine.newspaper.network.ApiService
import com.orgustine.newspaper.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(
    private val apiService: ApiService
) {

    suspend fun getAllNews(
        section : String,
        timePeriod : Int) : Flow<DataState<News>> = flow{

        emit(DataState.Waiting)
        try {
            val response = apiService.getNews(section, timePeriod, API_KEY)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}