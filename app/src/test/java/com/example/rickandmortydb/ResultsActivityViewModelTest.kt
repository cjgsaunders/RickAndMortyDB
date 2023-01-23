package com.example.rickandmortydb

import com.example.rickandmortydb.domain.objects.SearchResult
import com.example.rickandmortydb.domain.repository.SearchRepository
import com.example.rickandmortydb.domain.util.Resource
import com.example.rickandmortydb.presentation.results.ResultsActivityViewModel
import com.example.rickandmortydb.presentation.results.ResultsActivityViewModel.ResultsState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


class ResultsActivityViewModelTest {

    @RelaxedMockK
    private lateinit var repository: SearchRepository

    @RelaxedMockK
    private lateinit var data: SearchResult

    private lateinit var sut: ResultsActivityViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initSut()
    }

    @Test
    fun GIVEN_the_view_model_is_initialised_THEN_check_the_states_are_correct() {

        initSut()

        val actual = ResultsState(
            sut.state.value.isLoading,
            sut.state.value.searchData,
            sut.state.value.errorMessage
        )

        val expected = ResultsState(
            isLoading = true,
            searchData = null,
            errorMessage = null
        )

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun GIVEN_loadSearchInfo_is_called_WHEN_data_loaded_from_api_THEN_update_states_accordingly() {

        coEvery { repository.getCharacterSearch("rick", null) } returns Resource.Success(data)

        runBlocking { sut.loadSearchInfo("rick")  }
        Thread.sleep(100) //takes a microsecond to update states

        val expected = ResultsState(
            isLoading = false,
            searchData = data,
            errorMessage = null
        )

        val actual = ResultsState(
            sut.state.value.isLoading,
            sut.state.value.searchData,
            sut.state.value.errorMessage
        )

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun GIVEN_loadSearchInfo_is_called_WHEN_error_loading_from_api_THEN_update_states_accordingly() {

        val errorMessage = "message"
        coEvery { repository.getCharacterSearch("rick", null) } returns Resource.Error(
            errorMessage
        )

        sut.loadSearchInfo("rick")
        Thread.sleep(100) //takes a microsecond to update states

        val actual = ResultsState(
            sut.state.value.isLoading,
            sut.state.value.searchData,
            sut.state.value.errorMessage
        )

        val expected = ResultsState(
            isLoading = false,
            searchData = null,
            errorMessage = errorMessage
        )

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun GIVEN_getRandomCharactersTestable_is_called_WHEN_data_loaded_from_api_THEN_update_states_accordingly() {

        coEvery { repository.getCharacterById("1,2,3") } returns Resource.Success(data)

        runBlocking {
            sut.getRandomCharactersTestable()
        }

        Thread.sleep(100)

        val expected = ResultsState(
            isLoading = false,
            searchData = data,
            errorMessage = null
        )

        val actual = ResultsState(
            sut.state.value.isLoading,
            sut.state.value.searchData,
            sut.state.value.errorMessage
        )

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun GIVEN_getRandomCharactersTestable_is_called_WHEN_error_loading_from_api_THEN_update_states_accordingly() {

        val errorMessage = "message"
        coEvery { repository.getCharacterById("1,2,3") } returns Resource.Error(errorMessage)

        runBlocking {
            sut.getRandomCharactersTestable()
        }

        Thread.sleep(100)

        val expected = ResultsState(
            isLoading = false,
            searchData = null,
            errorMessage = errorMessage
        )

        val actual = ResultsState(
            sut.state.value.isLoading,
            sut.state.value.searchData,
            sut.state.value.errorMessage
        )

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun GIVEN_loadSearchInfo_is_called_THEN_getCharacterSearch_is_called_only_once() {

        sut.loadSearchInfo("rick")

        verify(exactly = 1) {
            runBlocking {
                repository.getCharacterSearch("rick", null)
            }
        }
    }


    @Test
    fun GIVEN_getRandomCharactersTestable_is_called_THEN_getCharacterById_is_called_only_once() {

        sut.getRandomCharactersTestable()

        verify(exactly = 1) {
            runBlocking {
                repository.getCharacterById("1,2,3")
            }
        }
    }

    private fun initSut() {
        sut = ResultsActivityViewModel(
            repository
        )
    }
}