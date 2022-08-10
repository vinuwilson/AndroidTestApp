package com.example.androidtest.movielist.viewmodel

import androidx.lifecycle.*
import com.example.androidtest.movielist.model.Movie
import com.example.androidtest.movielist.network.MovieRepository
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val movieList = liveData<Result<List<Movie>>> {
        loader.postValue(true)
        emitSource(repository.getMovieList().onEach {
            loader.postValue(false)
        }.asLiveData())
    }
}
