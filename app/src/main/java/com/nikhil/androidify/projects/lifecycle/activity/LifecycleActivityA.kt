package com.nikhil.androidify.projects.lifecycle.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityLifecycleABinding
import timber.log.Timber

/**
 * Common scenarios:
 * 1. Press 'Home' and come back
 * 2. Press 'Recents' and come back
 * 3. ??
 */
class LifecycleActivityA : AppCompatActivity() {

    private lateinit var binding: ActivityLifecycleABinding

    private val TAG = "LifecycleActivityA_Logs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG -> onCreate()")

        binding = ActivityLifecycleABinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnA.setOnClickListener {
            startActivity(Intent(this, LifecycleActivityB::class.java))
        }
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
//        finish()
        super.onStop()
    }

    // "onDestroy()" is not guaranteed to be called everytime app is killed[tested myself]
    override fun onDestroy() {
        Timber.d("$TAG -> onDestroy()")
        super.onDestroy()
    }
}
