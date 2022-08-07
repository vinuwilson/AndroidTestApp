package com.example.androidtest

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieService {
    suspend fun fetchMovieList() : Flow<Result<List<Movie>>> {
        return flow {

        }
    }

}
