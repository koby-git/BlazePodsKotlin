package com.koby.blazepodskotlin.repository

import androidx.lifecycle.LiveData
import com.koby.blazepodskotlin.data.local.AppDatabase
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.data.remote.MyAppService
import com.koby.blazepodskotlin.util.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit
import javax.inject.Inject

class Repository
@Inject constructor(
    val appDatabase: AppDatabase,
   val retrofit: MyAppService
) : RepositoryInterface{

    override fun getItemListFromRetrofit():Flow<RemoteResult<List<Item>>> = flow {
       val list = retrofit.getItemsList()
        emit(RemoteResult.success(list))
    }

    override suspend fun initItemList(){
       val list = retrofit.getItemsList()
        appDatabase.appDao().insertAll(list)
    }

    override fun saveFavoriteItem(item: Item) {
        item.favorite = true
        appDatabase.appDao().insertItem(item)
    }

    override fun getFavoriteItem(): LiveData<List<Item>> {
        return appDatabase.appDao().getAllFavoriteItems()
    }

    override suspend fun insertItem() {

    }

    override fun getItemListFromRoom():LiveData<List<Item>>{
        return appDatabase.appDao().getAllItems()
    }

    override suspend fun saveInCache(items: List<Item>) {
        appDatabase.appDao().insertAll(items)
    }
}