package com.jonathansteele.spacexlaunch.shared

import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlNormalizedCacheFactory = SqlNormalizedCacheFactory("apollo.db")
}