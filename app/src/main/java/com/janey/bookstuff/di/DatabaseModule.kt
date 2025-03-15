package com.janey.bookstuff.di

import android.content.Context
import com.janey.bookstuff.database.BookDatabase
import com.janey.bookstuff.database.dao.TBRBookDao
import com.janey.bookstuff.tbr.data.TBRRepository
import com.janey.bookstuff.tbr.data.TBRRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context) =
        BookDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideLibraryBookDao(bookDatabase: BookDatabase) = bookDatabase.libraryBookDao()

    @Provides
    @Singleton
    fun provideReadBookDao(bookDatabase: BookDatabase) = bookDatabase.readBookDao()

    @Provides
    @Singleton
    fun provideGoalDao(bookDatabase: BookDatabase) = bookDatabase.goalDao()

    @Provides
    @Singleton
    fun provideTBRBookDao(bookDatabase: BookDatabase) = bookDatabase.tbrDao()

    @Provides
    @Singleton
    fun provideTBRRepository(tbrBookDao: TBRBookDao): TBRRepository = TBRRepositoryImpl(tbrBookDao)
}