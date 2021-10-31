package com.koby.blazepodskotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.util.RemoteResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RepositoryTest : RepositoryInterface {

    var roomItemList = mutableListOf<Item>()
    var roomFavoriteItemList = mutableListOf<Item>()

    var shouldReturnNetworkError = false

    val _roomLiveData = MutableLiveData<List<Item>>()
    val roomLiveData:LiveData<List<Item>> = _roomLiveData

    override fun getItemListFromRetrofit(): Flow<RemoteResult<List<Item>>> = flow {
        val list = ArrayList<Item>()
        list.add(
            Item(
                1, "FAKE_NAME",
                "FAKE_POST_TITLE"
            )
        )
        emit(RemoteResult.success(list))
    }

    override fun getItemListFromRoom(): LiveData<List<Item>> = liveData {
        emit(roomItemList)
    }

    override suspend fun saveInCache(items: List<Item>) {
        roomItemList.addAll(items)
    }

    override suspend fun initItemList() {
        val list = ArrayList<Item>()
        list.add(
            Item(
                1, "FAKE_NAME",
                "FAKE_POST_TITLE"
            )
        )
        list.add(
            Item(
                2, "FAKE_NAME",
                "FAKE_POST_TITLE"
            )
        )
        list.add(
            Item(
                3, "FAKE_NAME",
                "FAKE_POST_TITLE"
            )
        )
        roomItemList.addAll(list)
    }

    override fun saveFavoriteItem(item: Item) {
        roomFavoriteItemList.add(item)
        _roomLiveData.postValue(roomFavoriteItemList)
    }

    override fun getFavoriteItem(): LiveData<List<Item>>{
        return roomLiveData
    }

    override suspend fun insertItem() {

    }

}