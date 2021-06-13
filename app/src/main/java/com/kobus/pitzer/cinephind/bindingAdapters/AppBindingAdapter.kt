package com.kobus.pitzer.cinephind.bindingAdapters

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kobus.pitzer.cinephind.R
import timber.log.Timber

//RecyclerViewBackground src
@BindingAdapter(value = ["backgroundColour"])
fun setRecyclerViewBackground(recyclerView: RecyclerView, itemType: String?) {
    Timber.i("setRecyclerViewBackground %s", itemType)
    when (itemType?.toLowerCase()) {
        "movie" -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.white_dark
            )
        )
        "series" -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.white_dark
            )
        )
        "season" -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.grey_light
            )
        )
        else -> recyclerView.setBackgroundColor(
            ContextCompat.getColor(
                recyclerView.context,
                R.color.white_dark
            )
        )
    }
}