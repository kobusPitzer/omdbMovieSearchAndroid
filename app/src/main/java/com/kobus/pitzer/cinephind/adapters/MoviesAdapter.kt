package com.kobus.pitzer.cinephind.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.adapters.viewholders.MoviesViewHolder
import com.kobus.pitzer.cinephind.interfaces.OnClickListener
import com.kobus.pitzer.cinephind.repository.models.SearchModel

class MoviesAdapter(val context: Context) :
    RecyclerView.Adapter<MoviesViewHolder>() {
    private var moviesList: List<SearchModel> = mutableListOf()
    var itemClickListener: OnClickListener<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie_list_item, parent, false)
        return MoviesViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        holder.onBindViewHolder(
            moviesList[position],
            position
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun getMoviesList(): List<SearchModel> {
        return moviesList
    }

    fun setMoviesList(newMoviesList: List<SearchModel>) {
        this.moviesList = newMoviesList
        notifyDataSetChanged()
    }
}