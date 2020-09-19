package com.nikhil.androidify.codelabs.roomwithaview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nikhil.androidify.R

class WordActivity : AppCompatActivity() {

    /**
     * We want to open the NewWordActivity when tapping on the FAB and, once we are back in the MainActivity,
     * to either insert the new word in the database or show a Toast.
     *
     * To achieve this, let's start by defining a request code:
     */
    private val newWordActivityRequestCode = 1

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)
        title = getString(R.string.room)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /**
         * Use [ViewModelProvider] to associate your ViewModel with your Activity.
         *
         * When your Activity first starts, the [ViewModelProviders] will create the ViewModel.
         * When the activity is destroyed, for example through a configuration change, the ViewModel persists.
         * When the activity is re-created, the ViewModelProviders return the existing ViewModel.
         */
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        /**
         * The [onChanged] method (the default method for our Lambda) fires when the observed data changes and the activity is in the foreground:
         */
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@WordActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}