package com.jonathansteele.spacexlaunch.android

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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