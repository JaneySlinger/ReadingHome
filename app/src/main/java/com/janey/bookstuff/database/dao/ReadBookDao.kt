package com.janey.bookstuff.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.janey.bookstuff.database.entities.ReadBookEntity

@Dao
interface ReadBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: ReadBookEntity)

    @Update
    suspend fun updateBook(book: ReadBookEntity)

    @Delete
    suspend fun deleteBook(book: ReadBookEntity)

    @Query("SELECT * FROM read_books")
    suspend fun getAllBooks(): List<ReadBookEntity>

    @Query("SELECT * FROM read_books WHERE id = :id")
    suspend fun getBookById(id: Int): ReadBookEntity?

}