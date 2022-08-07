package com.example.androidtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var viewModelFactory: MovieViewModelFactory
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://movies-sample.herokuapp.com/api/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: MovieAPI = retrofit.create(MovieAPI::class.java)

    private val service = MovieService(api)
    private val repository = MovieRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        setupViewModel()

        viewModel.movieList.observe(this as LifecycleOwner) { movieList ->
            if (movieList.getOrNull() != null)
                setupList(view, movieList.getOrNull()!!)
        }

        return view
    }

    private fun setupList(
        view: View?,
        movieList: List<Movie>
    ) {
        with(view as RecyclerView) {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

            adapter = MyItemRecyclerViewAdapter(movieList)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = MovieViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }

    companion object {

        @JvmStatic
        fun newInstance() = MovieFragment().apply { }
    }
}