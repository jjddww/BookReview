package com.example.bookreview.api

import com.example.bookreview.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {
    @GET("/v1/search/book.json")
    fun getBookList(@Query("query") keyword : String)
    : Call<SearchBookDto>
}