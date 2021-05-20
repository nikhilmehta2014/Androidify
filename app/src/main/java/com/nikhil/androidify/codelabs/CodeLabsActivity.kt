package com.nikhil.androidify.codelabs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R
import com.nikhil.androidify.codelabs.coroutines.main.BasicCoroutinesActivity
import com.nikhil.androidify.codelabs.coroutinesadvanced.ui.AdvancedCoroutinesActivity
import com.nikhil.androidify.codelabs.databinding.basicsample.BasicSampleActivity
import com.nikhil.androidify.codelabs.roomwithaview.WordActivity
import com.nikhil.androidify.databinding.ActivityCodelabsBinding

class CodeLabsActivity :AppCompatActivity(){

    private lateinit var binding:ActivityCodelabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCodelabsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.activity_codeLabs)

        binding.tvRoom.setOnClickListener {
            startActivity(Intent(this, WordActivity::class.java))
        }
        binding.tvDataBinding.setOnClickListener {
            startActivity(Intent(this, BasicSampleActivity::class.java))
        }
        binding.tvBasicCoroutines.setOnClickListener {
            startActivity(Intent(this, BasicCoroutinesActivity::class.java))
        }
        binding.tvAdvancedCoroutines.setOnClickListener {
            startActivity(Intent(this, AdvancedCoroutinesActivity::class.java))
        }
    }
}