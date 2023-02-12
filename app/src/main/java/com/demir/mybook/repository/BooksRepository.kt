package com.demir.mybook.repository

import com.demir.mybook.data.BooksApi
import com.demir.mybook.model.BookResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class BooksRepository
@Inject constructor(val apiService:BooksApi){

    suspend fun getAllBooks(page:Long):Flow<BookResponse>{
        return flow {
            emit(apiService.getAllBooks(page))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun searchBooks(searchQuery:String):Flow<BookResponse>{
        return flow {
            emit(apiService.searchBooks(searchQuery))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun categoryBooks(category:String,page:Long):Flow<BookResponse>{
        return flow {
            emit(apiService.categoryBooks(page,category))
        }.flowOn(Dispatchers.IO)
    }


    /*
    suspend fun getAllBooks(page:String):Response<BookResponse>{
        return apiService.getAllBooks(page)
    }

     */
}