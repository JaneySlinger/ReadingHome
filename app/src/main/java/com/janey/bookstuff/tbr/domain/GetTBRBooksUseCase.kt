package com.janey.bookstuff.tbr.domain

import com.janey.bookstuff.tbr.TBRBook
import kotlinx.coroutines.flow.Flow

interface GetTBRBooksUseCase {
    suspend operator fun invoke(): Flow<List<TBRBook>>
}