package com.example.androidtest.movielist.network

import com.example.androidtest.movielist.model.MovieList
import retrofit2.http.GET

interface MovieAPI {

    @GET("movies")
    suspend fun fetchAllMovieList() : MovieList

}
