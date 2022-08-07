package com.example.androidtest.movielist.network

import com.example.androidtest.movielist.model.MovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieService @Inject constructor(
    private val api: MovieAPI
) {

    suspend fun fetchMovieList(): Flow<Result<MovieList>> {

        return flow {
            emit(Result.success(api.fetchAllMovieList()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
