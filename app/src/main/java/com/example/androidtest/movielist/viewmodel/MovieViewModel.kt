package com.example.androidtest.movielist.viewmodel

import androidx.lifecycle.*
import com.example.androidtest.movielist.model.Movie
import com.example.androidtest.movielist.network.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movieList = liveData<Result<List<Movie>>> {
        emitSource(repository.getMovieList().asLiveData())
    }
}
