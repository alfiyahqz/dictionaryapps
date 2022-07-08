package com.alfiyah.dictionaryapps.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class DictionaryColumns : BaseColumns{
        companion object {
            const val TABLE_NAME = "dictionary"
            const val _ID = "id"
            const val DIC_ID = "dic_id"
            const val DIC_WORD = "dic_word"
            const val DIC_TERM = "dic_term"
            const val DIC_SYNTAX = "dic_syntax"
            const val DIC_EXP_SYN = "dic_exp_syn"
            const val DIC_EXP_DESC = "dic_exp_desc"
        }
    }
}