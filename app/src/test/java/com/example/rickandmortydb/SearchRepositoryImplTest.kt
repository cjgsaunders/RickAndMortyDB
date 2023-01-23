package com.example.rickandmortydb

import com.example.rickandmortydb.data.remote.CharacterApi
import com.example.rickandmortydb.data.repository.SearchRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    @RelaxedMockK
    private lateinit var api: CharacterApi

    private lateinit var sut: SearchRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initSut()
    }

    @Test
    fun GIVEN_getCharacterSearch_called_THEN_correct_api_is_run_once(){
        runBlocking {
            sut.getCharacterSearch("rick", null)
        }

        verify(exactly = 1) { runBlocking {
            api.searchCharacters("rick", null)
        } }
    }

    @Test
    fun GIVEN_getCharacterById_called_THEN_correct_api_is_run_once(){
        runBlocking {
            sut.getCharacterById("1")
        }

        verify(exactly = 1) { runBlocking {
            api.getCharacterById("1")
        } }
    }

    private fun initSut() {
        sut = SearchRepositoryImpl(
            api
        )
    }
}