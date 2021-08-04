package com.jonathansteele.spacexlaunch.shared.cache

import com.jonathansteele.spacexlaunch.shared.entity.Links
import com.jonathansteele.spacexlaunch.shared.entity.Patch
import com.jonathansteele.spacexlaunch.shared.entity.RocketLaunch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.removeAllLaunches()
    }

    internal fun getAllLaunches(): List<RocketLaunch> =
        dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        articleUrl: String?,
        smallPatch: String?,
        largePatch: String?
    ): RocketLaunch = RocketLaunch(
        flightNumber = flightNumber.toInt(),
        name = missionName,
        details = details,
        launchDateUTC = launchDateUTC,
        success = launchSuccess,
        links = Links(
            patch = Patch(
                small = smallPatch,
                large = largePatch
            ),
            articleUrl = articleUrl
        )
    )

    internal fun createLaunches(launches: List<RocketLaunch>) {
        launches.forEach { launch ->
            insertLaunch(launch)
        }
    }

    private fun insertLaunch(launch: RocketLaunch) {
        dbQuery.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.name,
            details = launch.details,
            launchSuccess = launch.success ?: false,
            launchDateUTC = launch.launchDateUTC,
            articleUrl = launch.links.articleUrl,
            smallPatch = launch.links.patch.small,
            largePatch = launch.links.patch.large
        )
    }
}