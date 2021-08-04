package com.jonathansteele.spacexlaunch.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch(
    val name: String,
    @SerialName("date_utc")
    val launchDateUTC: String,
    val details: String?,
    @SerialName("flight_number")
    val flightNumber: Int,
    val success: Boolean?,
    val links: Links,
)

@Serializable
data class Links(
    @SerialName("article")
    val articleUrl: String?,
    val patch: Patch,
)

@Serializable
data class Patch(
    val large: String?,
    val small: String?
)
