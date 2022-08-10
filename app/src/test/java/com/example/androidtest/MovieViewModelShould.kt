package com.example.androidtest

import com.example.androidtest.movielist.model.Movie
import com.example.androidtest.movielist.network.MovieRepository
import com.example.androidtest.utils.BaseUnitTest
import com.example.androidtest.utils.getValueForTest
import com.example.androidtest.movielist.viewmodel.MovieViewModel
import com.example.androidtest.utils.captureValues
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieViewModelShould : BaseUnitTest() {

    private val repository: MovieRepository = mock()
    private val movieList = mock<List<Movie>>()
    private val expected = Result.success(movieList)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getMovieListFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCase()

        viewModel.movieList.getValueForTest()

        verify(repository, times(1)).getMovieList()
    }

    @Test
    fun emitMovieListFromRepository() = runBlocking {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.movieList.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(): Unit = runBlocking {
        val viewModel = mockFailureCase()

        assertEquals(exception, viewModel.movieList.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoading() = runBlocking {

        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.movieList.getValueForTest()

            Assert.assertEquals(true, values[0])
        }
    }

    @Test
    fun hideSpinnerAfterLoading() = runBlocking {

        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.movieList.getValueForTest()

            Assert.assertEquals(false, values.last())
        }
    }

    @Test
    fun hideSpinnerAfterError() = runBlocking {

        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.movieList.getValueForTest()

            Assert.assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): MovieViewModel {
        whenever(repository.getMovieList()).thenReturn(
            flow {
                emit(expected)
            }
        )
        return MovieViewModel(repository)
    }

    private suspend fun mockFailureCase(): MovieViewModel {
        whenever(repository.getMovieList()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        return MovieViewModel(repository)
    }

}