package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityFourthBinding

class FourthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        binding.btnD.setOnClickListener {
            startActivity(Intent(this, FifthActivity::class.java))
        }

    }
}
