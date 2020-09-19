package com.nikhil.androidify.codelabs.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * A simple VM for [BasicSampleActivity]
 */
class BasicSampleViewModel : ViewModel() {
    /**
     * With Data Binding, when an observable value changes, the UI elements it's bound to are updated automatically.
     */
    private val _name = MutableLiveData("Nikhil")
    private val _lastName = MutableLiveData("Mehta")
    private val _likes = MutableLiveData(0)

    val name: LiveData<String> = _name
    val lastName: LiveData<String> = _lastName
    val likes: LiveData<Int> = _likes

    /**
     * Increments the number of likes.
     *
     * As you can see, a [LiveData]'s value is set with the value property.
     */
    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }

    /**
     * Returns popularity in buckets: [Popularity.NORMAL], [Popularity.POPULAR] or [Popularity.STAR]
     *
     * You can make one [LiveData] depend on another using [Transformations].
     * This mechanism allows the library to update the UI when the value changes.
     */
    val popularity: LiveData<Popularity> = Transformations.map(_likes) {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }
}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}
