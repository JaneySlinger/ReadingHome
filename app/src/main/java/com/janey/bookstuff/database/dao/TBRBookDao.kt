package com.janey.bookstuff.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.janey.bookstuff.database.entities.TBRBookEntity

@Dao
interface TBRBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: TBRBookEntity)

    @Update
    suspend fun updateBook(book: TBRBookEntity)

    @Delete
    suspend fun deleteBook(book: TBRBookEntity)

    @Query("SELECT * FROM tbr_books")
    suspend fun getAllBooks(): List<TBRBookEntity>

    @Query("SELECT * FROM tbr_books WHERE id = :id")
    suspend fun getBookById(id: Int): TBRBookEntity?

}