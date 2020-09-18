package com.nikhil.androidify.launchmodes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nikhil.androidify.R
import kotlinx.android.synthetic.main.activity_fourth.*

class FourthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        Toast.makeText(this, "Task Id = $taskId", Toast.LENGTH_SHORT).show()

        btn_d.setOnClickListener {
            startActivity(Intent(this, FifthActivity::class.java))
        }

    }
}
