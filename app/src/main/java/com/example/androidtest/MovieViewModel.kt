package com.example.androidtest

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movieList = liveData<Result<List<Movie>>> {
        emitSource(repository.getMovieList().asLiveData())
    }
}
