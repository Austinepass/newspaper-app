package com.orgustine.newspaper.data.model

data class News(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)