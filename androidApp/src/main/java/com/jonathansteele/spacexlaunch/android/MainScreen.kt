package com.jonathansteele.spacexlaunch.android

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jonathansteele.spacexlaunch.shared.SpaceXRepository

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            SportsAppBottomNavigation(navController)
        },
    ) {
        val sdk = SpaceXRepository()
        MainScreenNavigationConfigurations(navController, sdk)
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    spaceXRepository: SpaceXRepository
) {
    NavHost(navController, startDestination = "Home") {
        composable("Home") {
            HomeTabs(repo = spaceXRepository)
        }
        composable("Upcoming") {
            UpcomingTabs(repo = spaceXRepository)
        }
        composable("Company") {
            CompanyTabs(repo = spaceXRepository)
        }
        composable("launchDetail/$id") {
            //LaunchDetail(sdk = spaceXRepository, id = )
        }
        composable("Vehicles") {
            VehiclesTab(repo = spaceXRepository)
        }
    }
}

@Composable
private fun SportsAppBottomNavigation(navController: NavHostController) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Event Icon") },
            label = { Text("Home") },
            selected = currentRoute == "Home",
            onClick = { navController.navigate("Home") },
        )
        BottomNavigationItem(
            icon = {
                Icon(painter = painterResource(id = R.drawable.ic_capsule),
                    contentDescription = "Capsule Icon")
            },
            label = { Text(text = "Vehicles") },
            selected = currentRoute == "Vehicles",
            onClick = { navController.navigate("Vehicles") }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_watch_later_24),
                    contentDescription = "Watch Later Icon"
                )
            },
            label = { Text("Upcoming") },
            selected = currentRoute == "Upcoming",
            onClick = { navController.navigate("Upcoming") }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_corporate_fare_24),
                    contentDescription = "Corporate Fare Icon"
                )
            },
            label = { Text("Company") },
            selected = currentRoute == "Company",
            onClick = { navController.navigate("Company") }
        )
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}