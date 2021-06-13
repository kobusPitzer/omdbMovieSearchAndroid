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
import com.kobus.pitzer.cinephind.MovieDetailActivity.Companion.ARG_MOVIE_ID
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.databinding.FragmentMovieDetailBinding
import com.kobus.pitzer.cinephind.repository.constant.Status
import com.kobus.pitzer.cinephind.repository.models.MovieDetail
import com.kobus.pitzer.cinephind.repository.viewmodels.MovieViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import org.koin.android.ext.android.inject
import timber.log.Timber

class MovieDetailViewFragment : Fragment() {
    private lateinit var movie: MovieDetail
    private var movieID: String = ""
    private lateinit var rootView: View
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModelMovies: MovieViewModel by inject()

    private val imageOptions = RequestOptions
        .bitmapTransform(
            BlurTransformation(25, 3)
        )


    private fun searchMovieByID(id: String?) {
        id?.let { viewModelMovies.searchMovieByID(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_movie_detail,
                container,
                false
            )

        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModelMovies

        movieID = if (savedInstanceState != null && savedInstanceState.containsKey(ARG_MOVIE_ID)) {
            savedInstanceState.getString(ARG_MOVIE_ID)!!
        } else {
            requireActivity().intent.getStringExtra(ARG_MOVIE_ID)!!
        }

        searchMovieByID(movieID)

        binding.favouriteOnClickHandler = View.OnClickListener {
            viewModelMovies.updateFavourite(movie)
        }

        viewModelMovies.movieSearched.observe(requireActivity(), Observer {
            if (it?.status == Status.SUCCESS || it.status == Status.CACHE && it.data != null) {
                Timber.i("Movie searched data returned")
                movie = it.data!!

                Glide.with(requireContext())
                    .load(it.data?.Poster)
                    .apply(imageOptions)
                    .into(binding.posterBG)

                Glide.with(requireContext())
                    .load(viewModelMovies.updateImageSizeIfPossible(it.data?.Poster))
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