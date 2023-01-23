package com.example.rickandmortydb.data.repository

import com.example.rickandmortydb.data.mappers.listToSearchResult
import com.example.rickandmortydb.data.mappers.toSearchResult
import com.example.rickandmortydb.data.remote.CharacterApi
import com.example.rickandmortydb.domain.objects.SearchResult
import com.example.rickandmortydb.domain.repository.SearchRepository
import com.example.rickandmortydb.domain.util.Resource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: CharacterApi
    ) : SearchRepository {
    override suspend fun getCharacterSearch(name: String, page: String?): Resource<SearchResult> {
        return try {
            Resource.Success(
                data = api.searchCharacters(
                    name = name,
                    page = page
                ).toSearchResult()
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun getCharacterById(id: String): Resource<SearchResult> {
        return try {
            Resource.Success(
                data = api.getCharacterById(
                    id = id
                ).listToSearchResult()
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error")
        }
    }
}


