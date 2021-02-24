package com.nikhil.androidify.projects.navdrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.R

class NavDrawerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}