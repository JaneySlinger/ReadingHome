package com.janey.bookstuff.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

class DatabaseConverters {

    @TypeConverter
    fun fromTimeStamp(value: Long?) : Date? {
        return value?.let {
            Date(value)
        }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?) : Long? {
        return date?.let {
            date.time
        }
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        return Gson().fromJson(json, Array<String>::class.java).toList()
    }
}