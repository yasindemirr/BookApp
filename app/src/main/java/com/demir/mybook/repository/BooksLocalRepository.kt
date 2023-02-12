package com.demir.mybook.repository

import com.demir.mybook.data.BooksDao
import com.demir.mybook.model.BooksLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksLocalRepository @Inject constructor(
    private val booksDao:BooksDao
) {
    fun readAllData(): Flow<List<BooksLocal>>{
        return booksDao.realAllData()
    }
    suspend fun upsertBook(book:BooksLocal) {
        booksDao.upsert(book)
    }
        /*
    suspend fun deleteBooks(list:List<BooksLocal>){
        booksDao.deleteBooks(list)
    }
         */
        suspend fun deleteBooks(books:BooksLocal){
            booksDao.deleteBooks(books)
        }
}