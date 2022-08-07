package com.example.androidtest

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieService(
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
