package com.example.rickandmortydb.domain.repository

import com.example.rickandmortydb.domain.objects.SearchResult
import com.example.rickandmortydb.domain.util.Resource

interface SearchRepository {
    suspend fun getCharacterSearch(name: String, page: String?) : Resource<SearchResult>

    suspend fun getCharacterById(id: String) : Resource<SearchResult>
}