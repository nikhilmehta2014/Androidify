package com.nikhil.androidify.codelabs.coroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TitleRepository provides an interface to fetch a title or request a new one be generated.
 *
 * Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make
 * when data is updated. You can consider repositories to be mediators between different data
 * sources, in our case it mediates between a network API and an offline database cache.
 */
class TitleRepository(private val network: BasicCoroutinesNetwork, private val titleDao: TitleDao) {

    /**
     * [LiveData] to load title.
     *
     * This is the main interface for loading a title. The title will be loaded from the offline
     * cache.
     *
     * Observing this will not cause the title to be refreshed, use [TitleRepository.refreshTitleWithCallbacks]
     * to refresh the title.
     */
    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }


    /**
     * Refresh the current title and save the results to the offline cache.
     *
     * This method does not return the new title. Use [TitleRepository.title] to observe
     * the current tile.
     */
    /**
     * `suspend` operator to tell Kotlin that it **works with coroutines**
     *
     * This implementation uses blocking calls for the network and database – but it's still a bit simpler than the callback version.
     *
     * For more details, check - [https://github.com/nikhilmehta2014/Androidify/blob/master/app/src/main/java/com/nikhil/androidify/codelabs/coroutines/README.md#replace-callback-with-coroutines]
     */
    suspend fun refreshTitle() {
        // interact with *blocking* network and IO calls from a coroutine
        withContext(Dispatchers.IO) {
            val result = try {
                // Make network request using a blocking call
                network.fetchNextTitle().execute()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw TitleRefreshError("Unable to refresh title", cause)
            }

            if (result.isSuccessful) {
                // Save it to database
                titleDao.insertTitle(Title(result.body()!!))
            } else {
                // If it's not successful, inform the callback of the error
                throw TitleRefreshError("Unable to refresh title", null)
            }
        }
    }

}

/**
 * Thrown when there was a error fetching a new title
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}
