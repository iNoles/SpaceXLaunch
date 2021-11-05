package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        VehiclesList(data = it)
    }
}

@Composable
fun VehiclesList(data: AllVehiclesQuery.Data) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Vehicles") }) }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                val roadster = data.roadster
                val roadsterLaunchDate = Instant.parse(roadster?.launch_date_utc)
                    .atOffset(ZoneOffset.UTC)
                    .atZoneSameInstant(ZoneId.systemDefault())
                Text(text = roadster?.name!!, style = MaterialTheme.typography.subtitle1)
                Text(
                    text = MediumDateFormatter.format(roadsterLaunchDate),
                    style = MaterialTheme.typography.body2
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
            data.dragonsFilterNotNull()?.let { dragonsList ->
                items(dragonsList) {
                    Text(text = it.name!!, style = MaterialTheme.typography.subtitle1)
                    Text(text = it.first_flight!!, style = MaterialTheme.typography.body2)
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            }
            data.rocketsFilterNotNull()?.let { rocketsList ->
                items(rocketsList) {
                    Text(text = it.name!!, style = MaterialTheme.typography.subtitle1)
                    Text(text = it.first_flight!!, style = MaterialTheme.typography.body2)
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            }
            data.shipsFilterNotNull()?.let { shipsList ->
                items(shipsList) {
                    Text(text = it.name!!, style = MaterialTheme.typography.subtitle1)
                    Text(text = "Ship Built: ".plus(it.year_built), style = MaterialTheme.typography.body2)
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    }
}