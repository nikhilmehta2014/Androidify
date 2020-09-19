package com.nikhil.androidify.projects.launchmodes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R
import kotlinx.android.synthetic.main.activity_fifth.*

class FifthActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        btn_e.setOnClickListener {
            startActivity(Intent(this, SixthActivity::class.java))
        }
    }
}