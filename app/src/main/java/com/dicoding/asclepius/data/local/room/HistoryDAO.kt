package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.asclepius.data.local.entity.HistoryEntity

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM histories")
    fun getHistories(): LiveData<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: HistoryEntity)

    @Update
    suspend fun updateHistories(history: HistoryEntity)

    @Query("DELETE FROM histories")
    suspend fun deleteHistory()
}