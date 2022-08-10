package com.example.androidtest.movielist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidtest.R
import com.example.androidtest.movielist.adapter.MyItemRecyclerViewAdapter
import com.example.androidtest.movielist.model.Movie
import com.example.androidtest.movielist.viewmodel.MovieViewModel
import com.example.androidtest.movielist.viewmodel.MovieViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        setupViewModel()

        observeLoading()

        observeMovieList()

        return view
    }

    private fun observeLoading() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE
            }
        }
    }

    private fun observeMovieList() {
        viewModel.movieList.observe(this as LifecycleOwner) { movieList ->
            if (movieList.getOrNull() != null)
                setupList(requireView().findViewById(R.id.movie_list), movieList.getOrNull()!!)
        }
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
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }

    companion object {

        @JvmStatic
        fun newInstance() = MovieFragment().apply { }
    }
}