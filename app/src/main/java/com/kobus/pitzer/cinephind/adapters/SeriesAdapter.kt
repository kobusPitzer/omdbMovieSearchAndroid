package com.kobus.pitzer.cinephind.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.adapters.viewholders.SeriesViewHolder
import com.kobus.pitzer.cinephind.interfaces.OnClickListener
import com.kobus.pitzer.cinephind.repository.models.SearchModel

class SeriesAdapter(val context: Context) :
    RecyclerView.Adapter<SeriesViewHolder>() {
    private var seriesList: List<SearchModel> = mutableListOf()
    var itemClickListener: OnClickListener<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_series_list_item, parent, false)
        return SeriesViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        holder.onBindViewHolder(
            seriesList[position],
            position
        )
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

    fun getSeriesList(): List<SearchModel> {
        return seriesList
    }

    fun setSeriesList(newSeriesList: List<SearchModel>) {
        this.seriesList = newSeriesList
        notifyDataSetChanged()
    }
}