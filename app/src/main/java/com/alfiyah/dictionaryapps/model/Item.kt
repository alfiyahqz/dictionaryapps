package com.alfiyah.dictionaryapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Item (
     var id: String = "",
     var term: String = "",
     var word: String = "",
     var syntax: String  = "",
     var exp_syntax: String = "",
     var exp_desc: String = ""

):Parcelable