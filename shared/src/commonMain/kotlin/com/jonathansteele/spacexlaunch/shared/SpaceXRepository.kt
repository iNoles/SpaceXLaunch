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
    private val apolloClient = ApolloClient(serverURL)

    fun getLaunches(): Flow<ApolloResponse<GetAllLaunchesQuery.Data>> =
        apolloClient.queryAsFlow(ApolloRequest(GetAllLaunchesQuery()))

    fun getUpcomingLaunches(): Flow<ApolloResponse<GetUpcomingLaunchesQuery.Data>> =
         apolloClient.queryAsFlow(ApolloRequest(GetUpcomingLaunchesQuery()))

    fun getCompany(): Flow<ApolloResponse<GetCompanyInfoQuery.Data>> =
        apolloClient.queryAsFlow(ApolloRequest(GetCompanyInfoQuery()))

    fun getLaunch(withId: String): Flow<ApolloResponse<GetLaunchDetailsQuery.Data>> =
        apolloClient.queryAsFlow(ApolloRequest(GetLaunchDetailsQuery(withId)))

    fun getVehicles(): Flow<ApolloResponse<AllVehiclesQuery.Data>> =
        apolloClient.queryAsFlow(ApolloRequest(AllVehiclesQuery()))

    private val mainScope = MainScope() // Uses by iOS

    fun getLaunches(success: (GetAllLaunchesQuery.Data) -> Unit) {
        mainScope.launch {
            getLaunches().collect { success(it.dataOrThrow) }
        }
    }

    fun getUpcomingLaunches(success: (GetUpcomingLaunchesQuery.Data) -> Unit) {
        mainScope.launch {
            getUpcomingLaunches().collect { success(it.dataOrThrow) }
        }
    }

    fun getCompany(success: (GetCompanyInfoQuery.Data) -> Unit) {
        mainScope.launch {
            getCompany().collect { success(it.dataOrThrow) }
        }
    }

    fun getVehicles(success: (AllVehiclesQuery.Data) -> Unit) {
        mainScope.launch {
            getVehicles().collect { success(it.dataOrThrow) }
        }
    }
}
