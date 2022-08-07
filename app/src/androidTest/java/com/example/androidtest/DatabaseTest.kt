package com.example.androidtest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.androidtest.movielist.db.MovieDao
import com.example.androidtest.movielist.db.MovieDatabase
import com.example.androidtest.utils.BaseUITest
import com.example.androidtest.utils.provideTestList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DatabaseTest : BaseUITest() {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, MovieDatabase::class.java
        ).build()
        movieDao = database.movieDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadAlbum() = runBlocking {
        movieDao.insertRecord(provideTestList())
        val movieListFromDb = movieDao.getAllRecords()
        assert(movieListFromDb.size == 3)
        assert(movieListFromDb[0].id == provideTestList()[0].id)
    }


    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }
}