package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        binding.btnB.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }
}
