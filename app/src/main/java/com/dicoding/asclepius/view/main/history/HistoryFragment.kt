package com.dicoding.asclepius.view.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapters.HistoryAdapter
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.helper.ViewModelFactory

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    private val historyViewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.getHistories().observe(viewLifecycleOwner) { histories ->
            if (histories == null) {
                binding?.tvNoData?.visibility = View.VISIBLE
            } else {
                binding?.tvNoData?.visibility = View.INVISIBLE
                val adapter = HistoryAdapter()
                adapter.submitList(histories)
                binding?.rvHistory?.layoutManager = LinearLayoutManager(requireActivity())
                binding?.rvHistory?.adapter = adapter
            }
        }

        binding?.fabClearHistory?.setOnClickListener{
            historyViewModel.deleteHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}