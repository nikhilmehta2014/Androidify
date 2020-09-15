package com.nikhil.androidify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nikhil.androidify.fragments.NormalFragment
import timber.log.Timber

/**
 * Common scenarios:
 * 1. Press 'Home' and come back
 * 2. Press 'Recents' and come back
 * 3. ??
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("$TAG -> onCreate()")
        setupFragment()
    }

    override fun onRestart() {
        Timber.d("$TAG -> onRestart()")
        super.onRestart()
    }

    override fun onStart() {
        Timber.d("$TAG -> onStart()")
        super.onStart()
    }

    override fun onResume() {
        Timber.d("$TAG -> onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.d("$TAG -> onPause()")
        super.onPause()
    }

    override fun onStop() {
        Timber.d("$TAG -> onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.d("$TAG -> onDestroy()")
        super.onDestroy()
    }

    /**
     * Adding a [Fragment] Dynamically
     */
    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.contentFrame, NormalFragment.newInstance(), TAG)
            .commit()
    }
}
