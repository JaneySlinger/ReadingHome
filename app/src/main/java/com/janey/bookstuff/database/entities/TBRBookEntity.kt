package com.janey.bookstuff.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.janey.bookstuff.database.DatabaseConverters
import java.util.Date

@TypeConverters(DatabaseConverters::class)
@Entity(tableName = "tbr_books")
data class TBRBookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "author") val author: String = "",
    @ColumnInfo(name = "image_url") val imageUrl: String = "",
    @ColumnInfo(name = "genres") val genres: List<String> = emptyList(),
    @ColumnInfo(name = "release_date") val releaseDate: Date? = null,
    @ColumnInfo(name = "is_released") val isReleased: Boolean? = null,
    @ColumnInfo(name = "reason_for_interest") val reasonForInterest: String = "",
    @ColumnInfo(name = "pages") val pages: Int = 0,
    @ColumnInfo(name = "date_added") val dateAdded: Date = Date(),
    @ColumnInfo(name="description") val description: String? = null
)