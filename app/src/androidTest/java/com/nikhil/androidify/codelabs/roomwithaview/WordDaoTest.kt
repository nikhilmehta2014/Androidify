package com.nikhil.androidify.codelabs.roomwithaview

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

/**
 * This is not meant to be a full set of tests. For simplicity, most of the samples do not include tests.
 *
 * However, when building the Room, it is helpful to make sure it works before adding the UI.
 */
@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    /**
     * TODO - Question: What is the meaning of "@get:Rule" ??
     *
     * apply [Rule] annotation to property getter
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wordDao: WordDao
    private lateinit var db: WordRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        wordDao = db.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() = runTest {
        val word = Word("word")
        wordDao.insert(word)
        val allWords = wordDao.getAlphabetizedWords().waitForValue()
        assertEquals(allWords[0].word, word.word)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() = runTest {
        val word = Word("aaa")
        wordDao.insert(word)
        val word2 = Word("bbb")
        wordDao.insert(word2)
        val allWords = wordDao.getAlphabetizedWords().waitForValue()
        assertEquals(allWords[0].word, word.word)
        assertEquals(allWords[1].word, word2.word)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runTest {
        val word = Word("word")
        wordDao.insert(word)
        val word2 = Word("word2")
        wordDao.insert(word2)
        wordDao.deleteAll()
        val allWords = wordDao.getAlphabetizedWords().waitForValue()
        assertTrue(allWords.isEmpty())
    }
}