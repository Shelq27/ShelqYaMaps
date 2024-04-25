package ru.netology.shelqyamaps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.shelqyamaps.dto.Mark
@Entity
data class MarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val name: String,
) {
    companion object {
        fun fromDto(dto: Mark): MarkEntity = with(dto) {
            MarkEntity(id = id, latitude = latitude, longitude = longitude, name = name)
        }
    }

    fun toDto(): Mark = Mark(id = id, latitude = latitude, longitude = longitude, name = name)
}