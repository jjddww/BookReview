package com.example.bookreview.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreview.model.History

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM History")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM HISTORY WHERE keyword = :keyword")
    fun deleteHistory(keyword: String?)
}