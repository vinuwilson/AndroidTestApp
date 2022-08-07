package com.example.androidtest

import com.example.androidtest.movielist.model.MovieList
import com.example.androidtest.movielist.network.MovieAPI
import com.example.androidtest.movielist.network.MovieService
import com.example.androidtest.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MovieServiceShould : BaseUnitTest() {

    private val api: MovieAPI = mock()
    private val movieList: MovieList = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getMovieListFromAPI(): Unit = runBlocking {

        val service = MovieService(api)

        service.fetchMovieList().first()

        verify(api, times(1)).fetchAllMovieList()
    }

    @Test
    fun convertValueToFlowResultAndEmitThem(): Unit = runBlocking {

        val service = mockSuccessfulCase()

        assertEquals(Result.success(movieList), service.fetchMovieList().first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlocking {

        val service = mockFailureCase()

        assertEquals(exception.message, service.fetchMovieList().first().exceptionOrNull()!!.message)
    }

    private suspend fun mockSuccessfulCase(): MovieService {
        whenever(api.fetchAllMovieList()).thenReturn(movieList)

        return MovieService(api)
    }

    private suspend fun mockFailureCase(): MovieService {
        whenever(api.fetchAllMovieList()).thenThrow(RuntimeException("Damn backend developer"))

        return MovieService(api)
    }
}