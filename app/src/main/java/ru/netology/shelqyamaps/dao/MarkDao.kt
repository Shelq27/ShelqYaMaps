package ru.netology.shelqyamaps.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.shelqyamaps.entity.MarkEntity

@Dao
interface MarkDao {
    @Query("SELECT * FROM MarkEntity")
    fun getAll(): Flow<List<MarkEntity>>

    @Query("DELETE FROM MarkEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mark: MarkEntity)
}