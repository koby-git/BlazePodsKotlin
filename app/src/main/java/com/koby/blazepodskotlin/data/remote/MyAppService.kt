package com.koby.blazepodskotlin.data.remote

import com.koby.blazepodskotlin.data.model.Item
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyAppService {

    @GET("posts")
    suspend fun getItemsList(): List<Item>

}