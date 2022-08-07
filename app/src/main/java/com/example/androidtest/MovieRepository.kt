package com.example.androidtest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val movieDao: MovieDao
) {
    suspend fun getMovieList(): Flow<Result<List<Movie>>> {

        return flow {
            if (service.fetchMovieList().first().isFailure) {
                if (movieDao.getAllRecords().isNotEmpty()) {
                    emit(Result.success(movieDao.getAllRecords()))
                }
            } else {
                emit(Result.success(service.fetchMovieList().first().getOrNull()!!.data))
                insertRecord(service.fetchMovieList().first().getOrNull()!!.data)
            }
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

    private suspend fun insertRecord(movie: List<Movie>) {
        withContext(Dispatchers.IO) {
            movieDao.deleteAllRecords()
            movieDao.insertRecord(movie)
        }
    }

}