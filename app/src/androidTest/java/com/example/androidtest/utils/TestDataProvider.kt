package com.example.androidtest.utils

import com.example.androidtest.Movie

fun provideTestList(): List<Movie> {
    val dummyList = arrayListOf<Movie>()
    dummyList.add(Movie("His", 1, "A", title = "ABC", year = "2001"))
    dummyList.add(Movie("Act", 2, "B", title = "PQR", year = "2000"))
    dummyList.add(Movie("Sci", 3, "C", title = "XYZ", year = "2021"))
    return dummyList
}