package com.jonathansteele.spacexlaunch.shared

import android.content.Context
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context)
}