package com.kobus.pitzer.cinephind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class SeriesDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val ARG_SERIES_ID = "ARG_SERIES_ID"

        fun generateIntent(context: Context, seriesID: String): Intent {
            val intent = Intent(context, SeriesDetailActivity::class.java)
            intent.putExtra(ARG_SERIES_ID, seriesID)
            return intent
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}