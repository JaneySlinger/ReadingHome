package com.janey.bookstuff.di

import com.janey.bookstuff.network.GoogleBooksApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideGoogleBooksRetrofit(
        moshi: Moshi
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(GOOGLE_BOOKS_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideGoogleBooksApi(
        retrofit: Retrofit
    ) = retrofit.create(GoogleBooksApi::class.java)
}