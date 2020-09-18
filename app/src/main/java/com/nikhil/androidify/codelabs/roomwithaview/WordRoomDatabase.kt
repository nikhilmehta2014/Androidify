package com.nikhil.androidify.codelabs.roomwithaview

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * What is a Room database?
 * 1. Room is a database layer on top of an SQLite database.
 * 2. Room takes care of mundane tasks that you used to handle with an [SQLiteOpenHelper].
 * 3. Room uses the DAO to issue queries to its database.
 * 4. By default, to avoid poor UI performance, Room doesn't allow you to issue queries on the main thread.
 * When Room queries return [LiveData], the queries automatically run asynchronously on a background thread.
 * 5. Room provides compile-time checks of SQLite statements.
 *
 * Your Room database class must be abstract and extend [RoomDatabase].
 * Usually, you only need one instance of a Room database for the whole app.
 *
 * In a real app, you should consider setting a directory for Room to use to export the schema so you can check the current schema into your version control system.
 * For a sample, a destroy and re-create strategy can be sufficient. But, for a real app, you must implement a migration strategy. See [Understanding migrations with Room](https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929).
 */
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    /**
     * The database exposes DAOs through an abstract "getter" method for each [Dao].
     */
    abstract fun wordDao(): WordDao

    companion object {
        /**
         * [Singleton] prevents multiple instances of database opening at the same time
         *
         * getDatabase() returns the singleton.
         * It'll create the database the first time it's accessed, using Room's database builder to create a [RoomDatabase] object
         * in the application context from the WordRoomDatabase class and names it "word_database".
         */
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}