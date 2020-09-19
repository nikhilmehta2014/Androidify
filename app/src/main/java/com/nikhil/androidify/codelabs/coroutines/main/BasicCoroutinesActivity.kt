package com.nikhil.androidify.codelabs.coroutines.main

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.nikhil.androidify.R

class BasicCoroutinesActivity : AppCompatActivity() {

    /**
     * Inflate [activity_basic_coroutines.xml] and setup data binding.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_coroutines)
        title = getString(R.string.basic_coroutines)

        val rootLayout: ConstraintLayout = findViewById(R.id.rootLayout)
        val title: TextView = findViewById(R.id.title)
        val taps: TextView = findViewById(R.id.taps)
        val spinner: ProgressBar = findViewById(R.id.spinner)

        // Get BasicCoroutinesViewModel by passing a database to the factory
        val database = getDatabase(this)
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        val viewModel = ViewModelProviders
            .of(this, BasicCoroutinesViewModel.FACTORY(repository))
            .get(BasicCoroutinesViewModel::class.java)

        // When rootLayout is clicked call onMainViewClicked in ViewModel
        rootLayout.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        /**
         * update the title when the [BasicCoroutinesViewModel.title] changes
         */
        viewModel.title.observe(this, Observer { value ->
            value?.let {
                title.text = it
            }
        })

        viewModel.taps.observe(this, Observer { value ->
            taps.text = value
        })

        /**
         * show the spinner when [BasicCoroutinesViewModel.spinner] is true
         */
        viewModel.spinner.observe(this, Observer { value ->
            value.let { show ->
                spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        /**
         * Show a snackbar whenever the [BasicCoroutinesViewModel.snackbar] is updated a non-null value
         */
        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })
    }
}