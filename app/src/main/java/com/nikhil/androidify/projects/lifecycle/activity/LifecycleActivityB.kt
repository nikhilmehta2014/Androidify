package com.nikhil.androidify.projects.lifecycle.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityLifecycleBBinding
import timber.log.Timber

/**
 * Common scenarios:
 * 1. Press 'Home' and come back
 * 2. Press 'Recents' and come back
 * 3. ??
 */
class LifecycleActivityB : AppCompatActivity() {

    private lateinit var binding: ActivityLifecycleBBinding

    private val TAG = "LifecycleActivityB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG -> onCreate()")

        binding = ActivityLifecycleBBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}
