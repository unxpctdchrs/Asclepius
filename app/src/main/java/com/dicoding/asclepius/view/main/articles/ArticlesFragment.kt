package com.dicoding.asclepius.view.main.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapters.ArticlesAdapter
import com.dicoding.asclepius.data.remote.response.ArticlesResponse
import com.dicoding.asclepius.databinding.FragmentArticlesBinding
import com.dicoding.asclepius.helper.ViewModelFactory

class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding

    private val articlesViewModel by viewModels<ArticlesViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articlesViewModel.getArticles()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesViewModel.articles.observe(viewLifecycleOwner) { articles ->
            loadArticles(articles)
        }

        articlesViewModel.isLoading.observe(viewLifecycleOwner) {
            setLoading(it)
        }
    }

    private fun loadArticles(articles: ArticlesResponse) {
        val adapter = ArticlesAdapter()
        adapter.submitList(articles.articles)
        binding?.rvArticles?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvArticles?.adapter = adapter
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) binding?.loader?.visibility = View.VISIBLE else binding?.loader?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}