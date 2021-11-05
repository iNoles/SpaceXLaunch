package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jonathansteele.spacexlaunch.shared.GetUpcomingLaunchesQuery
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
fun UpcomingTabs(repo: SpaceXRepository, modifier: Modifier = Modifier) {
    val upcomingData = repo.getUpcomingLaunches().collectAsState(initial = null).value
    upcomingData?.data?.launchesFilterNotNull()?.let {
        UpcomingList(allLaunches = it, modifier = modifier)
    }
}

@Composable
fun UpcomingList(allLaunches: List<GetUpcomingLaunchesQuery.Launch>, modifier: Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Upcoming Launch") }) }
    ) {
        LazyColumn(contentPadding = it) {
            items(allLaunches) {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = 8.dp
                ) {
                    UpcomingListItem(launch = it, modifier = modifier)
                }
            }
        }
    }
}

@Composable
fun UpcomingListItem(launch: GetUpcomingLaunchesQuery.Launch, modifier: Modifier) {
    val date = Instant.ofEpochSecond(launch.date_unix?.toLong()!!)
        .atOffset(ZoneOffset.UTC)
        .atZoneSameInstant(ZoneId.systemDefault())
    val typography = MaterialTheme.typography
    Row(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Image(
            painter = rememberImagePainter(data = launch.links?.patch?.small),
            contentDescription = "small launch patch",
            modifier = modifier.size(90.dp)
        )
        Column(
            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp),
        ) {
            Text(text = launch.name!!, style = typography.subtitle1)
            Text(text = MediumDateFormatter.format(date), style = typography.body2)
        }
        Box(modifier = modifier.align(Alignment.CenterVertically)) {
            Text(text = "#".plus(launch.flight_number), style = typography.caption)
        }
    }
}