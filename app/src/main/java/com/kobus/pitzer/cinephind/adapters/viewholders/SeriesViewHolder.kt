package com.kobus.pitzer.cinephind.adapters.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.interfaces.OnClickListener
import com.kobus.pitzer.cinephind.repository.models.SearchModel

class SeriesViewHolder(val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.name)
    private val tvYear: TextView = itemView.findViewById(R.id.year)
    private val ivPoster: ImageView = itemView.findViewById(R.id.poster)
    var itemClickListener: OnClickListener<String>? = null
    private val imageOptions = RequestOptions()
        .transform(RoundedCorners(15))
        .placeholder(R.drawable.series_placeholder)


    fun onBindViewHolder(series: SearchModel, position: Int) {
        tvName.text = series.title
        tvYear.text = series.year

        var seriesURL: Any = series.posterURL
        if (seriesURL.equals("N/A"))
            seriesURL = R.drawable.series_placeholder
        Glide.with(itemView)
            .load(seriesURL)
            .apply(imageOptions)
            .into(ivPoster)


        itemView.setOnClickListener {
            itemClickListener?.onItemClick(series.imdbID)
        }
    }
}