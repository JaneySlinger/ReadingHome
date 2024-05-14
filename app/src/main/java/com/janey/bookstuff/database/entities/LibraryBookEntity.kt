package com.janey.bookstuff.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.janey.bookstuff.database.DatabaseConverters
import java.util.Date

@TypeConverters(DatabaseConverters::class)
@Entity(tableName = "library_books")
data class LibraryBookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "author") val author: String = "",
    @ColumnInfo(name = "is_physical") val isPhysical: Boolean = true,
    @ColumnInfo(name = "is_ready_for_pickup") val isReadyForPickup: Boolean = true,
    @ColumnInfo(name = "due_date") val dueDate: Date? = null,
    @ColumnInfo(name = "expected_available_date") val expectedAvailableDate: Date? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String = "",
)