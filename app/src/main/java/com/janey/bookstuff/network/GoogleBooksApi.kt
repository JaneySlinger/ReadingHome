package com.janey.bookstuff.network

import com.janey.bookstuff.network.entities.GoogleBookResult
import com.janey.bookstuff.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun getBookFromQuery(
        @Query("key") key: String = BuildConfig.GOOGLE_BOOKS_API_KEY,
        @Query("q") query: String,
//        @Query("projection") projection: String = "lite"
    ): GoogleBookResult
}