package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceX_LaunchTheme
import com.jonathansteele.spacexlaunch.shared.GetAllLaunchesQuery
import com.jonathansteele.spacexlaunch.shared.SpaceXRepository
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
}

@Composable
fun HomeTabs(repo: SpaceXRepository, modifier: Modifier = Modifier) {
    val allLaunchesState = repo.getLaunches().collectAsState(initial = null).value
    allLaunchesState?.data?.launchesFilterNotNull()?.let {
        MainList(allLaunch = it, modifier = modifier)
    }
}

@Composable
fun MainList(allLaunch: List<GetAllLaunchesQuery.Launch?>, modifier: Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "SpaceX Launch") } ) }
    ) {
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(2000)
                refreshing = false
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = { refreshing = true },
        ) {
            LazyColumn(contentPadding = it) {
                items(allLaunch) {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = 8.dp
                    ) {
                        HomeListItem(launch = it, modifier = modifier)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeListItem(launch: GetAllLaunchesQuery.Launch?, modifier: Modifier) {
    val date = Instant.parse(launch?.date_utc)
        .atOffset(ZoneOffset.UTC)
        .atZoneSameInstant(ZoneId.systemDefault())
    val typography = MaterialTheme.typography
    Row(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Image(
            painter = rememberImagePainter(data = launch?.links?.patch?.small),
            contentDescription = "small launch patch",
            modifier = modifier.size(90.dp)
        )
        Column(
            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp),
        ) {
            Text(text = launch?.name!!, style = typography.subtitle1)
            Text(text = MediumDateFormatter.format(date), style = typography.body2)
        }
        Box(modifier = modifier.align(Alignment.CenterVertically)) {
            Text(text = "#".plus(launch?.flight_number), style = typography.caption)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTabsPreview() {
    SpaceX_LaunchTheme {
        val launch = GetAllLaunchesQuery.Launch(
            id = "5eb87cd9ffd86e000604b32a",
            name = "FalconSat",
            date_utc = "2006-03-24T22:30:00.000Z",
            flight_number = 1,
            links = GetAllLaunchesQuery.Links(
                article = "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html",
                patch = GetAllLaunchesQuery.Patch(
                    small = "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"
                )
            )
        )
        MainList(allLaunch = listOf(launch), modifier = Modifier)
    }
}