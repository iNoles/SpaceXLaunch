package com.jonathansteele.spacexlaunch.shared.network

import com.jonathansteele.spacexlaunch.shared.entity.RocketLaunch
import fuel.Fuel
import fuel.get
import fuel.serialization.toJson
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer

private const val LAUNCHES_ENDPOINT = "https://api.spacexdata.com/v5/launches"

suspend fun getAllLaunches(): List<RocketLaunch> {
    val json = Json { ignoreUnknownKeys = true }
    val fuel = Fuel.get(LAUNCHES_ENDPOINT)
    return fuel.toJson(json, ListSerializer(RocketLaunch.serializer()))
}