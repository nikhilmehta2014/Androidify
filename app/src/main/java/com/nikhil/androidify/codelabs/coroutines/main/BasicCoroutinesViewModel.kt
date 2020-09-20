package com.nikhil.androidify.codelabs.coroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhil.androidify.codelabs.coroutines.util.BACKGROUND
import com.nikhil.androidify.codelabs.coroutines.util.singleArgViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * BasicCoroutinesViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param repository the data source this ViewModel will fetch results from.
 */
class BasicCoroutinesViewModel(private val repository: TitleRepository) : ViewModel() {

    companion object {
        /**
         * Factory for creating [BasicCoroutinesViewModel]
         *
         * @param arg the repository to pass to [BasicCoroutinesViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::BasicCoroutinesViewModel)
    }

    /**
     * Request a snackbar to display a string.
     *
     * This variable is private because we don't want to expose MutableLiveData
     *
     * MutableLiveData allows anyone to set a value, and BasicCoroutinesViewModel is the only
     * class that should be setting values.
     */
    private val _snackBar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String?>
        get() = _snackBar

    /**
     * Update title text via this LiveData
     */
    val title = repository.title

    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Count of taps on the screen
     */
    private var tapCount = 0

    /**
     * LiveData with formatted tap count.
     */
    private val _taps = MutableLiveData<String>("$tapCount taps")

    /**
     * Public view of tap live data.
     */
    val taps: LiveData<String>
        get() = _taps

    /**
     * Respond to onClick events by refreshing the title.
     *
     * The loading spinner will display until a result is returned, and errors will trigger
     * a snackbar.
     */
    fun onMainViewClicked() {
        refreshTitle()
        updateTaps()
    }

    /**
     * This code does the same thing, waiting one second before showing a snackbar. However, there are some important differences:
     * 1. [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) will start a coroutine in the [viewModelScope].
     * This means when the job that we passed to [viewModelScope] gets canceled, all coroutines in this job/scope will be cancelled.
     * If the user left the Activity before [delay] returned, this coroutine will automatically be cancelled when
     * [onCleared] is called upon destruction of the ViewModel.
     * 2. Since [viewModelScope] has a default dispatcher of [Dispatchers.Main], this coroutine will be launched in the main thread.
     * 3. The function [delay] is a [suspend] function.
     * This is shown in Android Studio by the  icon in the left gutter.
     * Even though this coroutine runs on the main thread, delay won't block the thread for one second.
     * Instead, the dispatcher will schedule the coroutine to resume in one second at the next statement.
     */
    /**
     * Wait one second then display a snackbar.
     */
    private fun updateTaps() {
        // launch a coroutine in viewModelScope
        viewModelScope.launch {
            tapCount++
            // suspend this coroutine for one second
            delay(1_000)
            // resume in the main dispatcher
            // _snackbar.value can be called directly from main thread
            _taps.postValue("$tapCount taps")
        }
    }

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackBar.value = null
    }

    /**
     * Refresh the title, showing a loading spinner while it refreshes and errors via snackbar.
     */

    /** This function is called every time the user clicks on the screen – and
     * it will cause the repository to refresh the title and write the new title to the database.
     *
     * This implementation uses a callback to do a few things:
     * - Before it starts a query, it displays a loading spinner with `_spinner.value = true`
     * - When it gets a result, it clears the loading spinner with `_spinner.value = false`
     * - If it gets an error, it tells a snackbar to display and clears the spinner
     *
     * Note that the `onCompleted` callback is not passed the `title`.
     * Since we write all titles to the `Room` database, the UI updates to the current title by observing a `LiveData` that's updated by `Room`.
     *
     * In the update to coroutines, we'll keep the exact same behavior.
     * It's a **good pattern** to use an observable data source like a `Room` database to automatically keep the UI up to date.
     *
     * Check (https://github.com/nikhilmehta2014/Androidify/blob/master/app/src/main/java/com/nikhil/androidify/codelabs/coroutines/README.md#moving-from-callbacks-to-coroutines)
     * for more details.
     */
    private fun refreshTitle() {
        viewModelScope.launch {
            try {
                _spinner.value = true
                repository.refreshTitle()
            } catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}
