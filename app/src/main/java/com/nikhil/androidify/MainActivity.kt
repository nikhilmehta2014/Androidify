package com.nikhil.androidify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.codelabs.CodeLabsActivity
import com.nikhil.androidify.databinding.ActivityMainBinding
import com.nikhil.androidify.projects.TopicsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCodelabsProjects.setOnClickListener {
            startActivity(Intent(this, CodeLabsActivity::class.java))
        }
        binding.tvTopics.setOnClickListener {
            startActivity(Intent(this, TopicsActivity::class.java))
        }
    }
}