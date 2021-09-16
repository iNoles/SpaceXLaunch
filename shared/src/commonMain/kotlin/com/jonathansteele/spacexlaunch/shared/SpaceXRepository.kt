package com.jonathansteele.spacexlaunch.shared

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.CompiledField
import com.apollographql.apollo3.api.Executable
import com.apollographql.apollo3.cache.normalized.*

class SpaceXRepository(databaseDriverFactory: DatabaseDriverFactory) {
    private val serverURL = "https://graphql-spacex-server.herokuapp.com/graphql"

    private val objectIdGenerator = object : ObjectIdGenerator {
        override fun cacheKeyForObject(obj: Map<String, Any?>, context: ObjectIdGeneratorContext): CacheKey? =
            obj["id"]?.toString()?.let { CacheKey(it) } ?: TypePolicyObjectIdGenerator.cacheKeyForObject(obj, context)
    }

    private val cacheKeyResolver = object : CacheKeyResolver() {
        override fun cacheKeyForField(field: CompiledField, variables: Executable.Variables): CacheKey? =
            (field.resolveArgument("id", variables) as String?)?.let { CacheKey(it) }
    }

    private val apolloClient = ApolloClient(serverURL)
        .withNormalizedCache(
            normalizedCacheFactory = databaseDriverFactory.createDriver(),
            objectIdGenerator = objectIdGenerator,
            cacheResolver = cacheKeyResolver
        )

    suspend fun getLaunches(): List<GetAllLaunchesQuery.Launch?>? {
        val response = apolloClient.query(GetAllLaunchesQuery())
        return response.dataOrThrow.launches
    }
}
