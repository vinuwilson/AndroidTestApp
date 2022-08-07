package com.example.androidtest

import retrofit2.http.GET

interface MovieAPI {

    @GET("movies")
    suspend fun fetchAllMovieList() : MovieList

}
