package com.nikhil.androidify.launchmodes.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R

class SixthActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

    }
}