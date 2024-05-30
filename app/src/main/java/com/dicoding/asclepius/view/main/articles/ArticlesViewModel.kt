package com.dicoding.asclepius.view.main.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.Repository
import com.dicoding.asclepius.data.remote.response.ArticlesResponse

class ArticlesViewModel(private val repository: Repository) : ViewModel() {
    val articles: LiveData<ArticlesResponse> = repository.articles
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getArticles() = repository.getArticles()
}