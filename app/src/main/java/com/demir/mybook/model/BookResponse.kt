package com.demir.mybook.model

data class BookResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Books>
)