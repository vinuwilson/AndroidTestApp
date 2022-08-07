package com.example.androidtest

import com.example.androidtest.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test


class MovieRepositoryShould : BaseUnitTest() {

    private var service: MovieService = mock()
    private var movieList = mock<MovieList>()
    private val exception = RuntimeException("Something went wrong")
    private val movieDao: MovieDao = mock()

    @Test
    fun getMovieListFromService(): Unit = runBlocking {

        val repository = MovieRepository(service, movieDao)

        repository.getMovieList().first()

        verify(service, times(1)).fetchMovieList()
    }

    @Test
    fun emitMovieListFromService(): Unit = runBlocking {

        val repository = mockSuccessfulCase()

        assertEquals(movieList, repository.getMovieList().first())
    }

    @Test
    fun propagateError(): Unit = runBlocking {

        val repository = mockFailureCase()

        assertEquals(exception.message, repository.getMovieList().first().exceptionOrNull()!!.message)

    }

    private suspend fun mockSuccessfulCase(): MovieRepository {
        whenever(service.fetchMovieList()).thenReturn(
            flow {
                emit(Result.success(movieList))
            }
        )
        return MovieRepository(service, movieDao)
    }

    private suspend fun mockFailureCase(): MovieRepository {
        whenever(service.fetchMovieList()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        return MovieRepository(service, movieDao)
    }

}