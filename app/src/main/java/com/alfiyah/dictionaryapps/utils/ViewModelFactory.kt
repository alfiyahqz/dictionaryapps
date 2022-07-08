package com.alfiyah.dictionaryapps.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfiyah.dictionaryapps.bookmark.BookmarkViewModel
import com.alfiyah.dictionaryapps.db.DictionaryHelper
import com.alfiyah.dictionaryapps.detail.DetailViewModel

class ViewModelFactory private constructor(private val dictionaryHelper: DictionaryHelper) :
    ViewModelProvider.NewInstanceFactory() {

    val getHelper: DictionaryHelper get() = dictionaryHelper

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(DictionaryHelper.getInstance(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(dictionaryHelper) as T
            }
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                return BookmarkViewModel(dictionaryHelper) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}