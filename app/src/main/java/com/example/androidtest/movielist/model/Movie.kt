package com.example.androidtest.movielist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    val genre: String,
    @PrimaryKey
    val id: Int,
    val poster: String,
    val title: String,
    val year: String
)