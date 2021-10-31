package com.koby.blazepodskotlin.ui.list

import androidx.lifecycle.*
import com.koby.blazepodskotlin.data.model.Item
import com.koby.blazepodskotlin.repository.Repository
import com.koby.blazepodskotlin.repository.RepositoryInterface
import com.koby.blazepodskotlin.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel
    @Inject constructor(
        private val repository: RepositoryInterface
    ): ViewModel() {

    val itemList = repository.getItemListFromRetrofit()
        .onEach { item ->
            if (item.status == RemoteResult.Status.SUCCESS) {
                viewModelScope.launch {
                    item.data?.let { repository.saveInCache(it) }
                }
            }
        }
        .onStart { emit(RemoteResult.loading("loading string")) }
        .distinctUntilChanged()
        .asLiveData()


    val roomItemList: LiveData<List<Item>> = repository.getItemListFromRoom();

    fun initItemList() {
        viewModelScope.launch {
            repository.initItemList()
        }
    }

    fun saveFavoriteItem(item: Item) {
        repository.saveFavoriteItem(item)
    }

    fun getFavoriteItems(): LiveData<List<Item>> =
        repository.getFavoriteItem()

    fun insertItem() {

        viewModelScope.launch {
            repository.insertItem()
        }
    }


}