package com.janey.bookstuff.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.janey.bookstuff.database.dao.GoalDao
import com.janey.bookstuff.database.dao.LibraryBookDao
import com.janey.bookstuff.database.dao.ReadBookDao
import com.janey.bookstuff.database.dao.TBRBookDao
import com.janey.bookstuff.database.entities.GoalEntity
import com.janey.bookstuff.database.entities.LibraryBookEntity
import com.janey.bookstuff.database.entities.TBRBookEntity
import com.janey.bookstuff.database.entities.ReadBookEntity


/*
Have a table for each of the things that I need to store information about
- Library books
- Read books
- Goals
- TBR books
*/

@Database(entities = [LibraryBookEntity::class, ReadBookEntity::class, GoalEntity::class, TBRBookEntity::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun libraryBookDao(): LibraryBookDao
    abstract fun readBookDao(): ReadBookDao
    abstract fun goalDao(): GoalDao
    abstract fun tbrDao(): TBRBookDao
    companion object {
        @Volatile
        private var Instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookDatabase::class.java, "books_database")
            }
                .fallbackToDestructiveMigration()
                .build()
                .also { Instance = it }
        }
    }
}