package com.alfiyah.dictionaryapps.detail

import android.content.ContentValues
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfiyah.dictionaryapps.db.DatabaseContract
import com.alfiyah.dictionaryapps.db.DictionaryHelper
import com.alfiyah.dictionaryapps.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dictionaryHelper: DictionaryHelper) : ViewModel() {
    fun insertDictionary(item: Item): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val values = ContentValues().apply {
                put(DatabaseContract.DictionaryColumns.DIC_ID, item.id)
                put(DatabaseContract.DictionaryColumns.DIC_WORD, item.word)
                put(DatabaseContract.DictionaryColumns.DIC_TERM, item.term)
                put(DatabaseContract.DictionaryColumns.DIC_SYNTAX, item.syntax)
                put(DatabaseContract.DictionaryColumns.DIC_EXP_SYN, item.exp_syntax)
                put(DatabaseContract.DictionaryColumns.DIC_EXP_DESC, item.exp_desc)
            }
            val insert = dictionaryHelper.insert(values)
            if (insert > 0) {
                checkIfDataBookmarked(item.id)
            }
            liveData.postValue(insert > 0)
        }
        return liveData
    }

    fun deleteDictionary(id: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val delete = dictionaryHelper.deleteById(id)
            if (delete > 0) {
                checkIfDataBookmarked(id)
            }
            liveData.postValue(delete > 0)
        }
        return liveData
    }

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookmarked: LiveData<Boolean> get() = _isBookmarked
    fun checkIfDataBookmarked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isBookmarked.postValue(dictionaryHelper.isExists(id))
        }
    }
}