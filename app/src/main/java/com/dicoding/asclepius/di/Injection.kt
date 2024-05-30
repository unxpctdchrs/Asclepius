package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.Repository
import com.dicoding.asclepius.data.local.room.AsclepiusDatabase

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = AsclepiusDatabase.getInstance(context)
        val historyDAO = database.historyDAO()
        return Repository.getInstance(historyDAO)
    }
}