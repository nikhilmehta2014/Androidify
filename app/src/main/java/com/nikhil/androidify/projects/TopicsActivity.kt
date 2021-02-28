package com.nikhil.androidify.projects

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R
import com.nikhil.androidify.databinding.ActivityTopicsBinding
import com.nikhil.androidify.projects.launchmodes.FirstActivity
import com.nikhil.androidify.projects.layouts.constraintlayout.ConstraintLayoutExample1
import com.nikhil.androidify.projects.lifecycle.LifecycleActivity
import com.nikhil.androidify.projects.navdrawer.NavDrawerActivity

class TopicsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopicsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTopicsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.activity_topics)

        binding.tvLifecycle.setOnClickListener {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }
        binding.tvLaunchModes.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }
        binding.tvConstraintLayoutEx1.setOnClickListener {
            startActivity(Intent(this, ConstraintLayoutExample1::class.java))
        }
        binding.tvNavDrawer.setOnClickListener {
            startActivity(Intent(this, NavDrawerActivity::class.java))
        }
    }
}