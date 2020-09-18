package com.nikhil.androidify.codelabs.roomwithaview

import androidx.lifecycle.LiveData

/**
 * A repository class abstracts access to multiple data sources.
 * A Repository class provides a clean API for data access to the rest of the application.
 *
 * A Repository manages queries and allows you to use multiple backends.
 * In the most common example, the Repository implements the logic for deciding whether to fetch data from a network or use results cached in a local database.
 */

/**
 * Declares the [WordDao] as a private property in the constructor.
 *
 * Pass in the DAO instead of the whole database, because you only need access to the DAO.
 */
class WordRepository(private val wordDao: WordDao) {

    /**
     * Room executes all queries on a separate thread
     *
     * Observed LiveData will notify the observer when the data has changed.
     */
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

}