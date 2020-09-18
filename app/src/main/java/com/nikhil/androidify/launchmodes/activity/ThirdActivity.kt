package com.nikhil.androidify.launchmodes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.nikhil.androidify.R

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        val cButton = findViewById<Button>(R.id.btn_c)
        cButton.setOnClickListener{
            startActivity(Intent(this, FourthActivity::class.java))
        }
    }
}
