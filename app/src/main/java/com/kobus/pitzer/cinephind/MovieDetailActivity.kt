package com.kobus.pitzer.cinephind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val ARG_MOVIE_ID = "ARG_MOVIE_ID"

        fun generateIntent(context: Context, movieID: String): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(ARG_MOVIE_ID, movieID)
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