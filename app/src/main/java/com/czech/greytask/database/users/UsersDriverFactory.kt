package com.czech.greytask.database.users

import android.content.Context
import com.czech.greytask.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class UsersDriverFactory(private val context: Context) {

    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "users.db")
    }
}