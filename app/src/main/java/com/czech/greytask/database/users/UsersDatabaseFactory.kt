package com.czech.greytask.database.users

import com.czech.greytask.Database
import com.czech.greytask.database.Repositories_Entity
import com.czech.greytask.utils.SQLDelightConverter

class UsersDatabaseFactory(private val driverFactory: UsersDriverFactory) {

    fun createDriver(): Database {
        return Database(
            driver = driverFactory.createDriver(),
            Repositories_EntityAdapter = Repositories_Entity.Adapter(
                topicsAdapter = SQLDelightConverter.listOfStringsAdapter
            )
        )
    }
}