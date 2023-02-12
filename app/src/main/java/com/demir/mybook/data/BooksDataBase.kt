package com.demir.mybook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demir.mybook.model.BooksLocal

@Database(entities = [BooksLocal::class], version = 1)
abstract class BooksDataBase:RoomDatabase() {
    abstract fun booksDao():BooksDao
}