package com.dicoding.asclepius.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.data.local.room.HistoryDAO
import com.dicoding.asclepius.data.remote.response.ArticlesResponse
import com.dicoding.asclepius.data.remote.retrofit.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(private val historyDAO: HistoryDAO) {
    private val _articles = MutableLiveData<ArticlesResponse>()
    val articles: LiveData<ArticlesResponse> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getArticles() {
        _isLoading.value = true
        val client = APIConfig.getApiService().getArticles()
        client.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _articles.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getHistories(): LiveData<List<HistoryEntity>> = historyDAO.getHistories()

    suspend fun saveToHistory(history: HistoryEntity) {
        historyDAO.insertHistory(history)
        historyDAO.updateHistories(history)
    }

    suspend fun deleteHistory() = historyDAO.deleteHistory()

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(historyDAO: HistoryDAO): Repository = instance ?: synchronized(this) {
            instance ?: Repository(historyDAO)
        }.also { instance = it }
    }
}