package com.alfiyah.dictionaryapps.utils

import android.database.Cursor
import com.alfiyah.dictionaryapps.db.DatabaseContract
import com.alfiyah.dictionaryapps.model.Item

object DataMapper {
    fun mapCursorToArrayList(cursor: Cursor?) : ArrayList<Item>{
        val itemList = ArrayList<Item>()

        cursor?.apply {
            while (moveToNext()){
                val id = getString(getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DIC_ID))
                val word = getString(getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DIC_WORD))
                val term = getString(getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DIC_TERM))
                val syntax = getString(getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DIC_SYNTAX))
                val exp_syntax = getString(getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DIC_EXP_SYN))
                val exp_desc = getString(getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DIC_EXP_DESC))

                itemList.add(Item(id, term, word, syntax, exp_syntax, exp_desc))
            }
        }

        return itemList
    }
}