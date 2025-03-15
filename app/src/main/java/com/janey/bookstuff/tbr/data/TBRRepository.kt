package com.janey.bookstuff.tbr.data

import com.janey.bookstuff.database.entities.TBRBookEntity
import kotlinx.coroutines.flow.Flow

interface TBRRepository {
    suspend fun getBooks(): Flow<List<TBRBookEntity>>
    suspend fun getBook(id: Int): Flow<TBRBookEntity?>
    suspend fun insertBook(book: TBRBookEntity)
}