package com.jonathansteele.spacexlaunch.android

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceX_LaunchTheme
import com.jonathansteele.spacexlaunch.shared.GetLaunchDetailsQuery
import com.jonathansteele.spacexlaunch.shared.SpaceXRepository

@Composable
fun LaunchDetail(sdk: SpaceXRepository, id: String) {
    val launchDetail = sdk.getLaunch(id).collectAsState(initial = null).value
    launchDetail?.dataOrThrow?.launch?.let {
        LaunchDetailScreen(it)
    }
}

@Composable
fun LaunchDetailScreen(launch: GetLaunchDetailsQuery.Launch) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = launch.name!!) } ) }
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun LaunchDetailPreview() {
    SpaceX_LaunchTheme {
        val launch = GetLaunchDetailsQuery.Launch(
            name = "FalconSat",
            date_utc = "2006-03-24T22:30:00.000Z",
            flight_number = 1,
            details = "SpaceX's 20th and final Crew Resupply Mission under the original NASA CRS contract, this mission brings essential supplies to the International Space Station using SpaceX's reusable Dragon spacecraft. It is the last scheduled flight of a Dragon 1 capsule. (CRS-21 and up under the new Commercial Resupply Services 2 contract will use Dragon 2.) The external payload for this mission is the Bartolomeo ISS external payload hosting platform. Falcon 9 and Dragon will launch from SLC-40, Cape Canaveral Air Force Station and the booster will land at LZ-1. The mission will be complete with return and recovery of the Dragon capsule and down cargo.",
            links = GetLaunchDetailsQuery.Links(
                article = "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html"
            )
        )
        LaunchDetailScreen(launch = launch)
    }
}
