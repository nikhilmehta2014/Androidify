package com.nikhil.androidify.codelabs.roomwithaview

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * In the DAO (data access object), you specify SQL queries and associate them with method calls.
 * The compiler checks the SQL and generates queries from convenience annotations for common queries, such as @Insert.
 *
 * The DAO must be an interface or abstract class.
 *
 * By default, all queries must be executed on a separate thread.
 *
 * Room has coroutines support, allowing your queries to be annotated with the suspend modifier and
 * then called from a coroutine or from another suspension function.
 */
@Dao
interface WordDao {

    /**
     * Getting all words ordered alphabetically
     * [Query] requires that you provide a SQL query as a string parameter to the annotation, allowing for complex read queries and other operations.
     *
     * Use a return value of type [LiveData] in your method description, and
     * Room generates all necessary code to update the [LiveData] when the database is updated.
     *
     * <Note>:
     * If you use [LiveData] independently from [Room], you have to manage updating the data.
     * LiveData has no publicly available methods to update the stored data.
     */
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    /**
     * Declares a [suspend] function to insert one word
     * The selected onConflict strategy ignores a new word if it's exactly the same as one already in the list.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    /**
     * Declares a [suspend] function to delete all the  words.
     * There is no convenience annotation for deleting multiple entities, so it's annotated with the generic [Query]
     */
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}