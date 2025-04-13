package com.janey.bookstuff.tbr.domain

import com.janey.bookstuff.tbr.TBRBook
import com.janey.bookstuff.tbr.data.TBRRepository
import com.janey.bookstuff.tbr.toTBRBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTBRBooksUseCaseImpl @Inject constructor(
    private val tbrRepository: TBRRepository
) : GetTBRBooksUseCase {
    override suspend operator fun invoke(): Flow<List<TBRBook>> =
        tbrRepository.getBooks().map {
            books -> books.map {
                book -> book.toTBRBook() } }
}