package com.example.bookreview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bookreview.dao.HistoryDAO
import com.example.bookreview.dao.ReviewDAO
import com.example.bookreview.model.History
import com.example.bookreview.model.Review

@Database(entities = [History::class, Review::class], version = 2)
abstract class AppDataBase : RoomDatabase(){
    abstract fun historyDao(): HistoryDAO
    abstract fun reviewDao(): ReviewDAO
}

fun getAppDataBase(context: Context): AppDataBase {

    val migration_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE 'REVIEW' ('id' INTEGER, 'review' TEXT," + "PRIMARY KEY('id'))")
        }

    }
    return Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "BookSearchDB"
    )

        .addMigrations(migration_2_3)
        .build()
}