package com.example.androidtest.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.movielist.model.Movie
import com.example.androidtest.databinding.FragmentMovieBinding
import com.example.androidtest.utils.loadImage

class MyItemRecyclerViewAdapter(
    private val values: List<Movie>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.movieImage.loadImage(item.poster)
        holder.movieTitle.text = item.title
        holder.movieGenre.text = item.genre
        holder.movieYear.text = item.year
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val movieImage: ImageView = binding.movieImage
        val movieTitle: TextView = binding.movieTitle
        val movieGenre: TextView = binding.movieGenre
        val movieYear: TextView = binding.movieYear
    }

}