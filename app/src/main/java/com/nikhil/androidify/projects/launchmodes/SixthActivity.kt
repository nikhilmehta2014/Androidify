package com.nikhil.androidify.projects.launchmodes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivitySixthBinding

class SixthActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySixthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySixthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

    }
}