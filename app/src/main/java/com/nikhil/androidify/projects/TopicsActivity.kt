package com.nikhil.androidify.projects

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R
import com.nikhil.androidify.projects.launchmodes.FirstActivity
import com.nikhil.androidify.projects.lifecycle.LifecycleActivity
import kotlinx.android.synthetic.main.activity_topics.*

class TopicsActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
        title = getString(R.string.activity_topics)

        tv_lifecycle.setOnClickListener {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }
        tv_launch_modes.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }
    }
}