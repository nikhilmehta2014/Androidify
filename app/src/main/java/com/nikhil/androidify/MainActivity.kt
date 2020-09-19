package com.nikhil.androidify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.codelabs.CodeLabsActivity
import com.nikhil.androidify.projects.TopicsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_codelabs_projects.setOnClickListener {
            startActivity(Intent(this, CodeLabsActivity::class.java))
        }
        tv_topics.setOnClickListener {
            startActivity(Intent(this, TopicsActivity::class.java))
        }
    }
}