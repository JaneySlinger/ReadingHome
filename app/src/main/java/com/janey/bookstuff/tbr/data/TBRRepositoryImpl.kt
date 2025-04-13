package com.janey.bookstuff.tbr.data

import com.janey.bookstuff.database.dao.TBRBookDao
import com.janey.bookstuff.database.entities.TBRBookEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TBRRepositoryImpl @Inject constructor(
    private val tbrBookDao: TBRBookDao
) : TBRRepository {
    override suspend fun getBooks(): Flow<List<TBRBookEntity>> = tbrBookDao.getAllBooks()

    override suspend fun getBookById(id: Long): Flow<TBRBookEntity?> = tbrBookDao.getBookById(id)

    override suspend fun insertBook(book: TBRBookEntity): Long = tbrBookDao.insertBook(book)
}
