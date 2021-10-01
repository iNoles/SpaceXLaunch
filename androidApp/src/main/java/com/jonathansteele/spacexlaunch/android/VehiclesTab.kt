package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jonathansteele.spacexlaunch.shared.AllVehiclesQuery
import com.jonathansteele.spacexlaunch.shared.SpaceXRepository
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
}

@Composable
fun VehiclesTab(repo: SpaceXRepository) {
    val allVehiclesState = repo.getVehicles().collectAsState(initial = null).value
    allVehiclesState?.dataOrThrow?.let {
        VehiclesList(it)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VehiclesList(data: AllVehiclesQuery.Data) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Vehicles") }) }
    ) {
        LazyColumn {
            item {
                val roadster = data.roadster
                val roadsterLaunchDate = Instant.parse(roadster?.launch_date_utc)
                    .atOffset(ZoneOffset.UTC)
                    .atZoneSameInstant(ZoneId.systemDefault())
                ListItem(
                    text = { Text(text = roadster?.name!!) },
                    secondaryText = {
                        Text(text = MediumDateFormatter.format(roadsterLaunchDate))
                    }
                )
            }
            items(data.dragonsFilterNotNull()!!) {
                ListItem(
                    text = { Text(text = it.name!!) },
                    secondaryText = {
                        Text(text = it.first_flight!!)
                    }
                )
            }
            items(data.rocketsFilterNotNull()!!) {
                ListItem(
                    text = { Text(text = it.name!!) },
                    secondaryText = {
                        Text(text = it.first_flight!!)
                    }
                )
            }
            items(data.shipsFilterNotNull()!!) {
                ListItem(
                    text = { Text(text = it.name!!) },
                    secondaryText = {
                        Text(text = "Ship Built: ".plus(it.year_built))
                    }
                )
            }
        }
    }
}