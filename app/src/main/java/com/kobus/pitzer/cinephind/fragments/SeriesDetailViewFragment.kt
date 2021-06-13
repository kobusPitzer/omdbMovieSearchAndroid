@file:Suppress("DEPRECATION")

package com.kobus.pitzer.cinephind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.SeriesDetailActivity.Companion.ARG_SERIES_ID
import com.kobus.pitzer.cinephind.databinding.FragmentSeriesDetailBinding
import com.kobus.pitzer.cinephind.repository.constant.Status
import com.kobus.pitzer.cinephind.repository.models.SeriesDetailModel
import com.kobus.pitzer.cinephind.repository.viewmodels.SeriesViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import org.koin.android.ext.android.inject
import timber.log.Timber

class SeriesDetailViewFragment : Fragment() {
    private lateinit var series: SeriesDetailModel
    private var seriesID: String = ""
    private lateinit var rootView: View
    private lateinit var binding: FragmentSeriesDetailBinding
    private val viewModelSeries: SeriesViewModel by inject()

    private val imageOptions = RequestOptions
        .bitmapTransform(
            BlurTransformation(25, 3)
        )


    private fun searchSeriesByID(id: String?) {
        id?.let { viewModelSeries.searchSeriesByID(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_series_detail,
                container,
                false
            )

        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModelSeries

        seriesID =
            if (savedInstanceState != null && savedInstanceState.containsKey(ARG_SERIES_ID)) {
                savedInstanceState.getString(ARG_SERIES_ID)!!
            } else {
                requireActivity().intent.getStringExtra(ARG_SERIES_ID)!!
            }

        searchSeriesByID(seriesID)

        binding.favouriteOnClickHandler = View.OnClickListener {
            viewModelSeries.updateFavourite(series)
        }

        viewModelSeries.seriesSearched.observe(requireActivity(), Observer {
            if (it?.status == Status.SUCCESS || it.status == Status.CACHE && it.data != null) {
                Timber.i("Series searched data returned")
                series = it.data!!

                Glide.with(requireContext())
                    .load(it.data?.Poster)
                    .apply(imageOptions)
                    .into(binding.posterBG)

                Glide.with(requireContext())
                    .load(viewModelSeries.updateImageSizeIfPossible(it.data?.Poster))
                    .thumbnail(
                        Glide.with(requireContext())
                            .load(it.data?.Poster)
                            .apply(imageOptions)
                    )
                    .into(binding.poster)
            }
        })
    }
}