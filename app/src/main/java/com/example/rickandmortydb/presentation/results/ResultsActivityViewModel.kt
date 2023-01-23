package com.example.rickandmortydb.presentation.results

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortydb.domain.objects.SearchResult
import com.example.rickandmortydb.domain.repository.SearchRepository
import com.example.rickandmortydb.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ResultsActivityViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _state = mutableStateOf(ResultsState())
    val state: State<ResultsState> = _state

    fun loadSearchInfo(character: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getCharacterSearch(character, null)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        searchData = result.data,
                        errorMessage = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        searchData = null
                    )
                }
            }
        }
    }

    fun getRandomCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val search = generateRandomNumberList().joinToString(",")
            when (val result = repository.getCharacterById(search)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        searchData = result.data,
                        errorMessage = null

                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        searchData = null
                    )
                }
            }
        }
    }

    fun getRandomCharactersTestable() {
        // The above function is not testable with mockK because of the random number
        // generator. This function is a duplicate of the above with the randomness removed.
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getCharacterById("1,2,3")) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        searchData = result.data,
                        errorMessage = null

                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        searchData = null
                    )
                }
            }
        }
    }

    private fun generateRandomNumberList(): MutableList<Int> {
        val result: MutableList<Int> = mutableListOf()
        repeat(20) {
            val randomNumber = Random.nextInt(826)
            result.add(randomNumber)
        }
        return result
    }

    data class ResultsState(
        val isLoading: Boolean = true,
        val searchData: SearchResult? = null,
        var errorMessage: String? = null
    )
}



