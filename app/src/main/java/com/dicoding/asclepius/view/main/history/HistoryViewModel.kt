package com.dicoding.asclepius.view.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.Repository
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    fun getHistories() = repository.getHistories()

    fun saveToHistory(history: HistoryEntity) {
        viewModelScope.launch {
            repository.saveToHistory(history)
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            repository.deleteHistory()
        }
    }
}