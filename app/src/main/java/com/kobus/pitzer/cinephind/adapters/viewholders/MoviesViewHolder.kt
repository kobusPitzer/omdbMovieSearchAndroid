package com.kobus.pitzer.cinephind.adapters.viewholders

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.interfaces.OnClickListener
import com.kobus.pitzer.cinephind.repository.models.SearchModel

class MoviesViewHolder(val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.name)
    private val tvYear: TextView = itemView.findViewById(R.id.year)
    private val ivPoster: ImageView = itemView.findViewById(R.id.poster)
    var itemClickListener: OnClickListener<String>? = null
    private val imageOptions = RequestOptions()
        .transform(RoundedCorners(15))
        .placeholder(R.drawable.movie_placeholder)


    fun onBindViewHolder(movie: SearchModel, position: Int) {
        tvName.text = movie.title
        tvYear.text = movie.year

        var movieURL: Any = movie.posterURL
        if (movieURL.equals("N/A"))
            movieURL = R.drawable.movie_placeholder
        Glide.with(itemView)
            .load(movieURL)
            .apply(imageOptions)
            .into(ivPoster)


        itemView.setOnClickListener {
            itemClickListener?.onItemClick(movie.imdbID)
        }
    }
}