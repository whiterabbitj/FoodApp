package com.example.shoplist.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemList(val itemName: String,
                    val itemuid: String,
                    val itemType:String,
                    var itemPrice:Double,
                    val itemImage:String,
                    val itemDesc:String,
                    val itemValues:String ) : Parcelable