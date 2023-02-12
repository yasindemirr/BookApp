package com.demir.mybook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demir.mybook.model.Books
import com.demir.mybook.model.BooksLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: BooksLocal)
    @Query("SELECT*FROM bookslocal ORDER BY id ASC")
    fun realAllData():Flow<List<BooksLocal>>
    @Delete
    //suspend fun deleteBooks(list: List<BooksLocal>)
    suspend fun deleteBooks(book: BooksLocal)
}