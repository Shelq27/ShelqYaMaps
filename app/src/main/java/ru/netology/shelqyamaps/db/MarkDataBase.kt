package ru.netology.shelqyamaps.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.shelqyamaps.dao.MarkDao
import ru.netology.shelqyamaps.entity.MarkEntity

@Database(entities = [MarkEntity::class], version = 1)
abstract class MarkDataBase : RoomDatabase() {
    abstract val markDao: MarkDao

    companion object {
        @Volatile
        private var INSTANCE: MarkDataBase? = null

        fun getInstance(context: Context): MarkDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, MarkDataBase::class.java, "mark_db")
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
    }
}