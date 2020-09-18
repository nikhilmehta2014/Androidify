package com.nikhil.androidify.codelabs.roomwithaview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class describes the Entity (which represents the SQLite table) for your words.
 * Each property in the class represents a column in the table.
 * Room will ultimately use these properties to both create the table and instantiate objects from rows in the database.
 *
 * [Entity] - Each @Entity class represents a SQLite table. Annotate your class declaration to indicate that it's an entity.
 *
 * [PrimaryKey] - Every entity needs a primary key. To keep things simple, each word acts as its own primary key.
 *
 * [ColumnInfo] - Specifies the name of the column in the table if you want it to be different from the name of the member variable.
 *
 * Every property that's stored in the database needs to have public visibility, which is the Kotlin default.
 *
 * You can autogenerate unique keys by annotating the primary key as follows:
 *   <code>
 *      @Entity(tableName = "word_table")
 *      class Word(
 *      @PrimaryKey(autoGenerate = true) val id: Int,
 *      @ColumnInfo(name = "word") val word: String
 *      )
 *   <code>
 */
@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)