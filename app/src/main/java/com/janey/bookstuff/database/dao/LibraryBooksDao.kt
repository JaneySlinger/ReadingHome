package com.janey.bookstuff.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.janey.bookstuff.database.entities.LibraryBookEntity

@Dao
interface LibraryBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: LibraryBookEntity)

    @Update
    suspend fun updateBook(book: LibraryBookEntity)

    @Delete
    suspend fun deleteBook(book: LibraryBookEntity)

    @Query("SELECT * FROM library_books")
    suspend fun getAllBooks(): List<LibraryBookEntity>

    @Query("SELECT * FROM library_books WHERE id = :id")
    suspend fun getBookById(id: Int): LibraryBookEntity?

}