package com.nikhil.androidify.codelabs.roomwithaview

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The [ViewModel]s role is to provide data to the UI and survive configuration changes.
 * A [ViewModel] acts as a communication center between the Repository and the UI.
 * You can also use a [ViewModel] to share data between fragments.
 *
 * A [ViewModel] holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
 * Separating your app's UI data from your [Activity] and [Fragment] classes lets you better follow the single responsibility principle:
 * Your activities and fragments are responsible for drawing data to the screen, while your ViewModel can take care of holding and processing all the data needed for the UI.
 */

/**
 * Using LiveData has several benefits:
 * 1. You can put an observer on the data (instead of polling for changes) and only update the the UI when the data actually changes.
 * 2. The Repository and the UI are completely separated by the [ViewModel].
 * 3. There are no database calls from the [ViewModel] (this is all handled in the Repository), making the code more testable.
 */

/**
 * [viewModelScope]
 *
 * In Kotlin, all coroutines run inside a [CoroutineScope].
 * A scope controls the lifetime of coroutines through its job.
 * When you cancel the job of a scope, it cancels all coroutines started in that scope.
 */

/**
 * Warning: Don't keep a reference to a context that has a shorter lifecycle than your ViewModel!
 * Examples are: [Activity], [Fragment], [View]
 * Keeping a reference can cause a memory leak, e.g. the ViewModel has a reference to a destroyed Activity!
 * If you need the application context (which has a lifecycle that lives as long as the application does), use AndroidViewModel.
 */

/**
 * Important: ViewModels don't survive the app's process being killed in the background when the OS needs more resources.
 * For UI data that needs to survive process death due to running out of resources, you can use the []Saved State module for ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate).
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WordRepository

    /**
     * In this example, LiveData is public as it will be observed from the UI layer.
     * Following best practices, a public LiveData is exposed and a private [MutableLiveData] is used inside the [ViewModel]
     */
    val allWords: LiveData<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    /**
     * Created a wrapper insert() method that calls the Repository's insert() method.
     * In this way, the implementation of insert() is encapsulated from the UI.
     *
     * We don't want insert to block the main thread, so we're launching a new coroutine and calling the repository's insert, which is a suspend function.
     */
    fun insert(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(word)
        }
    }
}