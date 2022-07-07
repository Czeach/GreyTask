package com.czech.greytask.database.repositories

import android.content.Context
import com.czech.greytask.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class ReposDriverFactory(private val context: Context) {

    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "repositories.db")
    }
}