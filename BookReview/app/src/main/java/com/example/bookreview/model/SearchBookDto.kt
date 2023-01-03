package com.example.bookreview.model

import com.google.gson.annotations.SerializedName

data class SearchBookDto(
    @SerializedName("items") val bookList: List<Book>
)
