package com.jonathansteele.spacexlaunch.shared

import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory

expect class DatabaseDriverFactory {
    fun createDriver(): SqlNormalizedCacheFactory
}