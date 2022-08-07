package com.example.androidtest

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService
) {
   suspend fun getMovieList(): Flow<Result<List<Movie>>> {

       return flow {
           emit(Result.success(service.fetchMovieList().first().getOrNull()!!.data))
       }.catch {
           emit(Result.failure(RuntimeException("Something went wrong")))
       }
    }

}