package com.jonathansteele.spacexlaunch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceX_LaunchTheme
import com.jonathansteele.spacexlaunch.shared.DatabaseDriverFactory
import com.jonathansteele.spacexlaunch.shared.GetAllLaunchesQuery
import com.jonathansteele.spacexlaunch.shared.SpaceXRepository
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceX_LaunchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainLayout()
                }
            }
        }
    }
}

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
}

@Composable
fun MainLayout() {
    val context = LocalContext.current
    val repo = remember { SpaceXRepository(DatabaseDriverFactory(context)) }
    val launch by produceState(initialValue = emptyList<GetAllLaunchesQuery.Launch?>(), repo) {
        value = repo.getLaunches()!!
    }
    MainList(launch = launch)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainList(launch: List<GetAllLaunchesQuery.Launch?>) {
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
            LazyColumn {
                items(launch) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = 8.dp
                    ) {
                        Row {
                            Image(
                                painter = rememberImagePainter(data = it?.links?.patch?.small),
                                contentDescription = "small launch patch",
                                modifier = Modifier.size(90.dp).align(Alignment.CenterVertically)
                            )
                            Column(
                                modifier = Modifier.padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(text = "Launch name: ".plus(it?.name!!))
                                when (it.success) {
                                    true -> Text(
                                        text = "Successful",
                                        style = TextStyle(color = Color.Green)
                                    )
                                    false -> Text(
                                        text = "Unsuccessful",
                                        style = TextStyle(color = Color.Red)
                                    )
                                    else -> Text(
                                        text = "No data",
                                        style = TextStyle(color = Color.Gray)
                                    )
                                }
                                Text(text = "Launch details: ".plus(it.details ?: ""))

                                val date = Instant.parse(it.date_utc).atOffset(ZoneOffset.UTC)
                                Text(text = MediumDateFormatter.format(date))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceX_LaunchTheme {
        val launch = GetAllLaunchesQuery.Launch(
            id = "5eb87cd9ffd86e000604b32a",
            name = "FalconSat",
            date_utc = "2006-03-24T22:30:00.000Z",
            details = "Engine failure at 33 seconds and loss of vehicle",
            flight_number = 1,
            success = false,
            links = GetAllLaunchesQuery.Links(
                article = "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html",
                patch = GetAllLaunchesQuery.Patch(
                    small = "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"
                )
            )
        )
        val list = listOf(launch)
        MainList(launch = list)
    }
}