package com.nikhil.androidify.projects.navdrawer

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.nikhil.androidify.R
import com.nikhil.androidify.databinding.ActivityNavDrawerBinding

class NavDrawerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        /**
         * Add an [ActionBarDrawerToggle], which displays the hamburger icon in the toolbar.
         */
        val drawerToggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close)
        binding.drawer.addDrawerListener(drawerToggle)
        // Set a simple drawable used for the left or right shadow
        binding.drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}