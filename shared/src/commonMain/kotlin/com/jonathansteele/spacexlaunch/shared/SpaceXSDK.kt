package com.jonathansteele.spacexlaunch.shared

import com.jonathansteele.spacexlaunch.shared.cache.Database
import com.jonathansteele.spacexlaunch.shared.cache.DatabaseDriverFactory
import com.jonathansteele.spacexlaunch.shared.entity.RocketLaunch
import com.jonathansteele.spacexlaunch.shared.network.getAllLaunches

class SpaceXSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}