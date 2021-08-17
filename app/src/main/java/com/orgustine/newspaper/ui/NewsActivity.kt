package com.orgustine.newspaper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.orgustine.newspaper.*
import com.orgustine.newspaper.data.NewsRepository
import com.orgustine.newspaper.data.NewsViewModel
import com.orgustine.newspaper.data.ViewModelFactory
import com.orgustine.newspaper.databinding.ActivityNewsBinding
import com.orgustine.newspaper.network.DataState
import com.orgustine.newspaper.network.RetrofitBuilder

class NewsActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var viewModel: NewsViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityNewsBinding
    private val newsAdapter = NewsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ViewModelFactory(NewsRepository(RetrofitBuilder.apiService))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)
        val section = intent.extras?.getString("text_input")
        val timePeriod = intent.extras?.getInt("time_period")

        viewModel.getNews(section!!, timePeriod!!)

        subscribe()
        binding.list.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }

    }

    private fun subscribe() {
        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Waiting -> {
                    Snackbar.make(
                        findViewById(android.R.id.content), "Fetching data...", Snackbar.LENGTH_LONG
                    ).show()
                }
                is DataState.Error -> {
                    Snackbar.make(
                        findViewById(android.R.id.content),
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
            this, "Item $id selected!", Toast.LENGTH_LONG
        ).show()
    }
}