package com.nikhil.androidify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class FourthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

    }
}
