package com.demir.mybook.di

import android.content.Context
import androidx.room.Room
import com.demir.mybook.BuildConfig
import com.demir.mybook.data.BooksApi
import com.demir.mybook.data.BooksDao
import com.demir.mybook.data.BooksDataBase
import com.demir.mybook.repository.BooksLocalRepository
import com.demir.mybook.repository.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val BASE_URL="https://gutendex.com/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .readTimeout(60,TimeUnit.SECONDS)
        .connectTimeout(60,TimeUnit.SECONDS)
        .writeTimeout(60,TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level=if(BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun providerApiService(retrofit: Retrofit):BooksApi{
        return retrofit.create(BooksApi::class.java)
    }
    @Provides
    fun providesAlertDao(booksDataBase: BooksDataBase):BooksDao = booksDataBase.booksDao()

    @Provides
    @Singleton
    fun providesAlertDatabase(@ApplicationContext context: Context):BooksDataBase
            = Room.databaseBuilder(context,BooksDataBase::class.java,"AlertDatabase")
        .allowMainThreadQueries().build()

    @Provides
    fun providesUserRepository(booksDao: BooksDao) : BooksLocalRepository
            = BooksLocalRepository(booksDao)


}