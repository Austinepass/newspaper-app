package com.orgustine.newspaper.network

import com.orgustine.newspaper.data.model.News
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//mostemailed/all-sections/1.json?api-key=anU1PoyWVXMlXpo1LCMp6Tu7C3gApbct
    @GET("mostemailed/{section}/{time-period}.json")
    suspend fun getNews(
    @Path("section") section : String,
    @Path("time-period") timePeriod : Int,
    @Query("api-key") apiKey : String) : News
}