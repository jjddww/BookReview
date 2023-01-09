package com.example.bookreview.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookreview.model.Review


@Dao
interface ReviewDAO {

    @Query("SELECT * FROM Review WHERE isbn = :isbn")
    fun getOne(isbn: String?): Review

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReview(review: Review)
}