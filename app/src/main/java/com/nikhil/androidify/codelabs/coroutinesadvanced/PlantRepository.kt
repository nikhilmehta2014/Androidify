/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nikhil.androidify.codelabs.coroutinesadvanced

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.nikhil.androidify.codelabs.coroutinesadvanced.utils.CacheOnSuccess
import com.nikhil.androidify.codelabs.coroutinesadvanced.utils.ComparablePair
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Repository module for handling data operations.
 *
 * [PlantRepository] provides a bridge between the [PlantListViewModel] and [PlantDao]
 *
 * Note: A Repository is a bridge between a ViewModel and the data
 *
 * This PlantRepository exposes two UI-observable database queries [plants] and
 * [getPlantsWithGrowZone].
 *
 * To update the plants cache, call [tryUpdateRecentPlantsForGrowZoneCache] or
 * [tryUpdateRecentPlantsCache].
 */
class PlantRepository private constructor(
    private val plantDao: PlantDao,
    private val plantService: NetworkService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    /**
     * Fetch a list of [Plant]s from the database.
     * Returns a LiveData-wrapped List of Plants.
     */
    /**
     * The [LiveData] builder allows us to calculate values asynchronously,
     * as [liveData] is backed by coroutines.
     * Here we have a [suspend] function to fetch a LiveData list of plants from the database,
     * while also calling a suspend function to get the custom sort order.
     * We then combine these two values to sort the list of plants and return the value,
     * all within the builder.
     *
     * Note: You can emit multiple values from a [LiveData] by calling the [emitSource] function
     * whenever you want to emit a new value.
     * Note that each call to [emitSource] removes the previously-added source.
     */
    val plants: LiveData<List<Plant>> = liveData<List<Plant>> {
        val plantsLiveData = plantDao.getPlants()
        val customSortOrder = plantsListSortOrderCache.getOrAwait()
        emitSource(plantsLiveData.map { plantList ->
            plantList.applySort(customSortOrder)
        })
    }

    /**
     * Fetch a list of [Plant]s from the database that matches a given [GrowZone].
     * Returns a LiveData-wrapped List of Plants.
     */
    /**
     * The coroutine starts execution when it is observed,
     * and is cancelled when the coroutine successfully finishes or
     * if the either the database or network call fails.
     *
     * If any of the suspend function calls fail, the entire block is canceled and not restarted,
     * which helps avoid leaks.
     */
    fun getPlantsWithGrowZone(growZone: GrowZone): LiveData<List<Plant>> = liveData {
        val plantsGrowZoneLiveData = plantDao.getPlantsWithGrowZoneNumber(growZone.number)
        val customSortOrder = plantsListSortOrderCache.getOrAwait()
        emitSource(plantsGrowZoneLiveData.map { plantList ->
            plantList.applySort(customSortOrder)
        })
    }

    /**
     * Returns true if we should make a network request.
     */
    private fun shouldUpdatePlantsCache(): Boolean {
        // suspending function, so you can e.g. check the status of the database here
        return true
    }

    /**
     * Update the plants cache.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentPlantsCache() {
        if (shouldUpdatePlantsCache()) fetchRecentPlants()
    }

    /**
     * Update the plants cache for a specific grow zone.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentPlantsForGrowZoneCache(growZoneNumber: GrowZone) {
        if (shouldUpdatePlantsCache()) fetchPlantsForGrowZone(growZoneNumber)
    }

    /**
     * Fetch a new list of plants from the network, and append them to [plantDao]
     */
    private suspend fun fetchRecentPlants() {
        val plants = plantService.allPlants()
        plantDao.insertAll(plants)
    }

    /**
     * Fetch a list of plants for a grow zone from the network, and append them to [plantDao]
     */
    private suspend fun fetchPlantsForGrowZone(growZone: GrowZone) {
        val plants = plantService.plantsByGrowZone(growZone)
        plantDao.insertAll(plants)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao, plantService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao, plantService).also { instance = it }
            }
    }

    /**
     * [plantsListSortOrderCache] is used as the in-memory cache for the custom sort order.
     * It will fallback to an empty list if there's a network error,
     * so that our app can still display data even if the sorting order isn't fetched.
     */
    private var plantsListSortOrderCache =
        CacheOnSuccess(onErrorFallback = { listOf<String>() }) {
            plantService.customPlantSortOrder()
        }

    /**
     * This extension function will rearrange the list,
     * placing [Plants] that are in the [customSortOrder] at the front of the list.
     */
    private fun List<Plant>.applySort(customSortOrder: List<String>): List<Plant> {
        return sortedBy { plant ->
            val positionForItem = customSortOrder.indexOf(plant.plantId).let { order ->
                if (order > -1) order else Int.MAX_VALUE
            }
            ComparablePair(positionForItem, plant.name)
        }
    }
}
