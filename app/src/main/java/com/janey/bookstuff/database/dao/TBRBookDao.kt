package com.janey.bookstuff.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.janey.bookstuff.database.entities.TBRBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TBRBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: TBRBookEntity)

    @Update
    fun updateBook(book: TBRBookEntity)

    @Delete
    fun deleteBook(book: TBRBookEntity)

    @Query("SELECT * FROM tbr_books")
    fun getAllBooks(): Flow<List<TBRBookEntity>>

    @Query("SELECT * FROM tbr_books WHERE id = :id")
    fun getBookById(id: Int): Flow<TBRBookEntity?>

}