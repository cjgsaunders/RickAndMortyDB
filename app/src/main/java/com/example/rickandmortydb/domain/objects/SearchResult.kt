package com.example.rickandmortydb.domain.objects

data class SearchResult(
    val totalResults: Int = 0,
    val nextPage: String?,
    val result: List<CharacterInfo>
)
