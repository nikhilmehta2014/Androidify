package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        var a: String = ""
        lateinit var abd: String

        binding.btnA.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
