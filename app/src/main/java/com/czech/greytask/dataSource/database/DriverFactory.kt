package com.czech.greytask.dataSource.database

import android.content.Context
import com.czech.greytask.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DriverFactory(private val context: Context) {

    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "greyTaskDatabase.db")
    }
}