package com.janey.bookstuff

import android.app.Application
import android.content.Context
import com.janey.bookstuff.database.BookDatabase
import com.janey.bookstuff.tbr.data.TBRRepository
import com.janey.bookstuff.tbr.data.TBRRepositoryImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookStuffApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        // Initialize any dependencies or libraries here
        container = AppDataContainer(this)
    }

}

interface AppContainer {
    val tbrRepository: TBRRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val tbrRepository: TBRRepository by lazy {
        TBRRepositoryImpl(BookDatabase.getDatabase(context).tbrDao())
    }
}
