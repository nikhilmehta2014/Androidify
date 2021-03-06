package com.nikhil.androidify.codelabs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R
import com.nikhil.androidify.codelabs.coroutines.main.BasicCoroutinesActivity
import com.nikhil.androidify.codelabs.coroutinesadvanced.ui.AdvancedCoroutinesActivity
import com.nikhil.androidify.codelabs.databinding.basicsample.BasicSampleActivity
import com.nikhil.androidify.codelabs.roomwithaview.WordActivity
import kotlinx.android.synthetic.main.activity_codelabs.*

class CodeLabsActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codelabs)
        title = getString(R.string.activity_codeLabs)

        tv_room.setOnClickListener {
            startActivity(Intent(this, WordActivity::class.java))
        }
        tv_data_binding.setOnClickListener {
            startActivity(Intent(this, BasicSampleActivity::class.java))
        }
        tv_basic_coroutines.setOnClickListener {
            startActivity(Intent(this, BasicCoroutinesActivity::class.java))
        }
        tv_advanced_coroutines.setOnClickListener {
            startActivity(Intent(this, AdvancedCoroutinesActivity::class.java))
        }
    }
}