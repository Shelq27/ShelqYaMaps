package ru.netology.shelqyamaps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.shelqyamaps.db.MarkDataBase
import ru.netology.shelqyamaps.dto.Mark
import ru.netology.shelqyamaps.entity.MarkEntity

class MapViewModel(context: Application) : AndroidViewModel(context) {
    private val dao = MarkDataBase.getInstance(context).markDao
    val marks = dao.getAll().map {
        it.map(MarkEntity::toDto)
    }

    fun insertMark(mark: Mark) {
        viewModelScope.launch {
            dao.insert(MarkEntity.fromDto(mark))
        }
    }

    fun deleteMarkById(id: Long) {
        viewModelScope.launch {
            dao.deleteById(id)
        }
    }

}