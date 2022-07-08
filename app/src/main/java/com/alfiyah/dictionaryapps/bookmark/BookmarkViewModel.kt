package com.alfiyah.dictionaryapps.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfiyah.dictionaryapps.db.DictionaryHelper
import com.alfiyah.dictionaryapps.model.Item
import com.alfiyah.dictionaryapps.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkViewModel(private val dictionaryHelper: DictionaryHelper) : ViewModel() {

    private val _getAllBookmark = MutableLiveData<List<Item>>()
    val getAllBookmark: LiveData<List<Item>> get() = _getAllBookmark
    fun queryBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            dictionaryHelper.open()

            val cursor = dictionaryHelper.queryAll()
            val list = DataMapper.mapCursorToArrayList(cursor)
            _getAllBookmark.postValue(list)

            dictionaryHelper.close()
        }
    }

}