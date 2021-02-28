package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityFifthBinding

class FifthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFifthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFifthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        binding.btnE.setOnClickListener {
            startActivity(Intent(this, SixthActivity::class.java))
        }
    }
}