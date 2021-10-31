package com.koby.blazepodskotlin.repository

import androidx.lifecycle.LiveData
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.util.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface RepositoryInterface {

    fun getItemListFromRetrofit(): Flow<RemoteResult<List<Item>>>
    fun getItemListFromRoom(): LiveData<List<Item>>

    suspend fun saveInCache(items: List<Item>)
    suspend fun initItemList()
     fun saveFavoriteItem(item: Item)
    fun getFavoriteItem() : LiveData<List<Item>>
    suspend fun insertItem()

}