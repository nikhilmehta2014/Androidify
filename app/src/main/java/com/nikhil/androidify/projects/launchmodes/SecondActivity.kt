package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.nikhil.androidify.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        val aButton = findViewById<Button>(R.id.btn_b)
        aButton.setOnClickListener{
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }
}
