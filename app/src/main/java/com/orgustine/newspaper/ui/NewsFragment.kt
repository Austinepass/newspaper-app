package com.orgustine.newspaper.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.orgustine.newspaper.*
import com.orgustine.newspaper.data.NewsRepository
import com.orgustine.newspaper.data.NewsViewModel
import com.orgustine.newspaper.data.ViewModelFactory
import com.orgustine.newspaper.databinding.FragmentNewsBinding
import com.orgustine.newspaper.network.DataState
import com.orgustine.newspaper.network.RetrofitBuilder

class NewsFragment : Fragment(R.layout.fragment_news), OnItemClickListener {
    private lateinit var viewModel: NewsViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: FragmentNewsBinding
    private val newsAdapter = NewsAdapter(this)
    private val args : NewsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        viewModelFactory = ViewModelFactory(NewsRepository(RetrofitBuilder.apiService))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        viewModel.getNews(args.section, args.time)

        subscribe()
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }


    private fun subscribe() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Waiting -> {
                    Snackbar.make(
                        requireView(), "Fetching data...", Snackbar.LENGTH_LONG
                    ).show()
                }
                is DataState.Error -> {
                    Snackbar.make(
                        requireView(),
                        "Error: ${dataState.exception.localizedMessage}",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") { viewModel.getNews("all-sections", 1) }
                        .show()
                }

                is DataState.Success -> {
                    newsAdapter.submitList(dataState.data.results)
                    binding.progressIndicator.visibility = View.GONE
                    binding.list.visibility = View.VISIBLE;
                }
            }
        })
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(
            requireContext(), "Item $id selected!", Toast.LENGTH_LONG
        ).show()
    }
}