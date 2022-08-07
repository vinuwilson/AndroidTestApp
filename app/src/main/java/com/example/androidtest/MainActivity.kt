package com.example.androidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtest.movielist.ui.MovieFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, MovieFragment.newInstance())
            .commit()
    }
}