package com.example.androidtest

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    suspend fun getAllRecords(): List<Movie>

    @Query("DELETE FROM movie")
    suspend fun deleteAllRecords()

}