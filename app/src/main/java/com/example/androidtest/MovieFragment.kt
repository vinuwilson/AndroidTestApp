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
import dagger.hilt.android.AndroidEntryPoint
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

        observeMovieList(view)

        return view
    }

    private fun observeMovieList(view: View?) {
        viewModel.movieList.observe(this as LifecycleOwner) { movieList ->
            if (movieList.getOrNull() != null)
                setupList(view, movieList.getOrNull()!!)
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