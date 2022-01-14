package com.jonathansteele.spacexlaunch.shared

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SpaceXRepository {
    private val serverURL = "https://graphql-spacex-server.herokuapp.com/graphql"
    private val apolloClient = ApolloClient.Builder().serverUrl(serverURL).build()

    fun getLaunches(): Flow<ApolloResponse<GetAllLaunchesQuery.Data>> =
        apolloClient.executeAsFlow(ApolloRequest.Builder(GetAllLaunchesQuery()).build())

    fun getUpcomingLaunches(): Flow<ApolloResponse<GetUpcomingLaunchesQuery.Data>> =
         apolloClient.executeAsFlow(ApolloRequest.Builder(GetUpcomingLaunchesQuery()).build())

    fun getCompany(): Flow<ApolloResponse<GetCompanyInfoQuery.Data>> =
        apolloClient.executeAsFlow(ApolloRequest.Builder(GetCompanyInfoQuery()).build())

    fun getLaunch(withId: String): Flow<ApolloResponse<GetLaunchDetailsQuery.Data>> =
        apolloClient.executeAsFlow(ApolloRequest.Builder(GetLaunchDetailsQuery(withId)).build())

    fun getVehicles(): Flow<ApolloResponse<AllVehiclesQuery.Data>> =
        apolloClient.executeAsFlow(ApolloRequest.Builder(AllVehiclesQuery()).build())

    private val mainScope = MainScope() // Uses by iOS

    fun getLaunches(success: (GetAllLaunchesQuery.Data) -> Unit) {
        mainScope.launch {
            getLaunches().collect { success(it.dataAssertNoErrors) }
        }
    }

    fun getUpcomingLaunches(success: (GetUpcomingLaunchesQuery.Data) -> Unit) {
        mainScope.launch {
            getUpcomingLaunches().collect { success(it.dataAssertNoErrors) }
        }
    }

    fun getCompany(success: (GetCompanyInfoQuery.Data) -> Unit) {
        mainScope.launch {
            getCompany().collect { success(it.dataAssertNoErrors) }
        }
    }

    fun getVehicles(success: (AllVehiclesQuery.Data) -> Unit) {
        mainScope.launch {
            getVehicles().collect { success(it.dataAssertNoErrors) }
        }
    }
}
