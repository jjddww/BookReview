package com.example.bookreview.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    @SerializedName("title") val title: String,

    @SerializedName("image") val imageLink: String,

    @SerializedName("author") val author: String,

    @SerializedName("discount") val price: String,

    @SerializedName("isbn") val id: String,

    @SerializedName("description") val desc: String
): Parcelable
