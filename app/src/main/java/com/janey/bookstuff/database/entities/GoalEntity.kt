package com.janey.bookstuff.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    // TODO update when I do this screen
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") val title: String = "",
    @ColumnInfo(name = "complete") val isComplete: Boolean = false,
)