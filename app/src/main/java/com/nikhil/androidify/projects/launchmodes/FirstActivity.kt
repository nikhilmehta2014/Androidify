package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.nikhil.androidify.R

class FirstActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        var a: String = ""
        lateinit var abd: String


        val aButton = findViewById<Button>(R.id.btn_a)
        aButton.setOnClickListener{
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
