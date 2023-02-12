package com.demir.mybook.data

import com.demir.mybook.model.BookResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("books")
    suspend fun getAllBooks(
    @Query("?page")page:Long
    ):BookResponse
    @GET("books")
    suspend fun searchBooks(
        @Query("search")searchQuery:String?
    ):BookResponse
    @GET("books")
    suspend fun categoryBooks(
        @Query("?page")page: Long,
        @Query("topic")category:String

    ):BookResponse


    /*
    @GET("books")
    suspend fun getAllBooks(
        @Query("page")page:String
    ):Response<BookResponse>

     */
}