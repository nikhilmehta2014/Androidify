package com.nikhil.androidify.projects.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityMainStaticBinding
import timber.log.Timber

/**
 * Common scenarios:
 * 1. Press 'Home' and come back
 * 2. Press 'Recents' and come back
 * 3. ??
 */
class LifecycleActivity : AppCompatActivity() {

    //    private lateinit var binding: ActivityMainBinding
    private lateinit var binding: ActivityMainStaticBinding

    private val TAG = "LifecycleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG -> onCreate()")

        //Add a [Fragment] Dynamically
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setupFragment()

        //Add a [Fragment] Statically
        binding = ActivityMainStaticBinding.inflate(layoutInflater)
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

    /**
     * Adding a [Fragment] Dynamically
     */
    /*private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.contentFrame, LifecycleFragment.newInstance(), TAG)
            .commit()
    }*/
}
