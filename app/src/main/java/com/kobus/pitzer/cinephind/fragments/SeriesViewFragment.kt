@file:Suppress("DEPRECATION")

package com.kobus.pitzer.cinephind.fragments

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kobus.pitzer.cinephind.R
import com.kobus.pitzer.cinephind.SeriesDetailActivity
import com.kobus.pitzer.cinephind.adapters.SeriesAdapter
import com.kobus.pitzer.cinephind.databinding.FragmentSeriesBinding
import com.kobus.pitzer.cinephind.interfaces.OnClickListener
import com.kobus.pitzer.cinephind.repository.constant.Status
import com.kobus.pitzer.cinephind.repository.models.SearchModel
import com.kobus.pitzer.cinephind.repository.viewmodels.RandomWordViewModel
import com.kobus.pitzer.cinephind.repository.viewmodels.SeriesViewModel
import com.squareup.seismic.ShakeDetector
import com.squareup.seismic.ShakeDetector.SENSITIVITY_LIGHT
import org.koin.android.ext.android.inject
import timber.log.Timber

class SeriesViewFragment : Fragment() {
    private lateinit var menuReference: Menu
    private lateinit var rootView: View
    private lateinit var binding: FragmentSeriesBinding
    private val viewModelSeries: SeriesViewModel by inject()
    private val viewModelWord: RandomWordViewModel by inject()
    private lateinit var seriesAdapter: SeriesAdapter
    private var networkDialog: AlertDialog? = null
    private var refreshDialog: AlertDialog? = null

    private var sd: ShakeDetector? = null
    private var sensorManager: SensorManager? = null

    private val networkCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (isAdded) {

                    Timber.i("Network available")
                    if (networkDialog != null && networkDialog?.isShowing!!) {
                        networkDialog?.dismiss()
                    }
                    requireActivity().runOnUiThread {
                        binding.ivNetworkState.visibility = View.GONE
                    }
                }
            }

            override fun onLost(network: Network) {
                Timber.i("Network Unavailable")
                showNoNetworkErrorDialog()
            }
        }


    private fun querySeries(searchText: String?) {
        if (searchText?.trim() != null && searchText.trim().length > 2) {
            hideSoftKeyBoard()
            searchText.trim().let { viewModelSeries.searchSeries(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_series,
                container,
                false
            )

        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModelSeries

        setRecyclerViewObserver()
        binding.emptyViewVisible = false

        registerNetworkCallback()

        if (!isNetworkAvailable()) {
            showNoNetworkErrorDialog()
        }

        sensorManager = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        sd = ShakeDetector(ShakeDetector.Listener {
            Timber.i("refresh")
            askToRefresh()
        })
        sd?.start(sensorManager)
        sd?.setSensitivity(SENSITIVITY_LIGHT)
    }

    private fun askToRefresh() {
        if (refreshDialog != null) {
            refreshDialog!!.dismiss()
        }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setCancelable(false)
        builder.setTitle(this.getString(R.string.shake_title))
        builder.setMessage(this.getString(R.string.shake_description))
        builder.setPositiveButton(this.getString(R.string.ok)) { _, _ ->
            if (refreshDialog != null) {
                requireActivity().runOnUiThread {
                    refreshDialog!!.dismiss()
                    viewModelWord.getRandomWord().observe(requireActivity(), Observer {
                        if (it?.status == Status.SUCCESS && it?.data != null)
                            searchForRandomMovie(it.data)
                    })
                }
            }
        }
        builder.setNegativeButton(this.getString(R.string.cancel)) { _, _ ->
            if (refreshDialog != null) {
                requireActivity().runOnUiThread {
                    refreshDialog!!.dismiss()
                }
            }
        }
        refreshDialog = builder.create()
        refreshDialog?.show()
    }

    private fun searchForRandomMovie(data: String) {
        val sItem = menuReference.findItem(R.id.action_search)
        val searchView = sItem.actionView as SearchView

        sItem.expandActionView()
        searchView.setQuery(data, true)
    }

    private fun launchSeriesDetailView(seriesID: String) {
        val intent = SeriesDetailActivity.generateIntent(requireActivity(), seriesID)
        requireActivity().startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        sd?.start(sensorManager)
    }

    override fun onPause() {
        sd?.stop()
        super.onPause()
    }


    private fun setupRecyclerView() {
        seriesAdapter = SeriesAdapter(requireActivity())
        binding.rvSeries.adapter = seriesAdapter
        binding.rvSeries.layoutManager = GridLayoutManager(requireActivity(), 2)
        seriesAdapter?.itemClickListener = object : OnClickListener<String> {
            override fun onItemClick(seriesID: String) {
                launchSeriesDetailView(seriesID)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)

        menuReference = menu

        val handler = Handler()

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                querySeries(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    querySeries(query)
                }, 1000)
                return true
            }
        })

        searchView.addOnAttachStateChangeListener(object :
            View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(arg0: View?) {
                // search was detached/closed
                viewModelSeries.searchSeries("series")
            }

            override fun onViewAttachedToWindow(arg0: View?) {
                // search was opened
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun hideSoftKeyBoard() {
        if (isAdded) {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (requireActivity().currentFocus != null && requireActivity().currentFocus!!.windowToken != null) {
                inputManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken,
                    0
                )
            }
            requireActivity().window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            )
        }
    }

    private fun setRecyclerViewObserver() {
        viewModelSeries.seriesSearchedValue.observe(requireActivity(), Observer {
            if (it.status == Status.SUCCESS || it.status == Status.CACHE && it.data != null) {
                binding.emptyViewVisible = it.data?.size!! < 1
                val seriesList = mutableListOf<SearchModel>()
                it.data?.forEach {
                    it?.let { it1 -> seriesList.add(it1) }
                }
                seriesAdapter.setSeriesList(seriesList)
                Timber.i("Series searched data returned")
            }
        })
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showNoNetworkErrorDialog() {
        requireActivity().runOnUiThread {
            binding.ivNetworkState.visibility = View.VISIBLE
        }
        if (networkDialog != null) {
            networkDialog!!.dismiss()
        }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setCancelable(false)
        builder.setTitle(this.getString(R.string.network_issue_title))
        builder.setMessage(this.getString(R.string.network_error_and_flight_mode))
        builder.setPositiveButton(this.getString(R.string.ok)) { _, _ ->
            if (networkDialog != null) {
                requireActivity().runOnUiThread {
                    networkDialog!!.dismiss()
                }
            }
        }
        networkDialog = builder.create()
        networkDialog?.show()
    }


    private fun registerNetworkCallback() {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }
}