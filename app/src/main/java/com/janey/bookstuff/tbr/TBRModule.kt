package com.janey.bookstuff.tbr

import com.janey.bookstuff.tbr.domain.GetTBRBooksUseCase
import com.janey.bookstuff.tbr.domain.GetTBRBooksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TBRModule {
    @Binds
    abstract fun bindGetTBRBooksUseCase(useCase: GetTBRBooksUseCaseImpl): GetTBRBooksUseCase
}