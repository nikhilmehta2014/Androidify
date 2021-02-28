package com.nikhil.androidify.projects.layouts.constraintlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.androidify.databinding.ActivityConstraintExample1Binding

/**
 * This class will demonstrate the [androidx.constraintlayout.widget.ConstraintLayout] margin gone property.
 */
class ConstraintLayoutExample1 : AppCompatActivity() {

    private lateinit var binding: ActivityConstraintExample1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConstraintExample1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}