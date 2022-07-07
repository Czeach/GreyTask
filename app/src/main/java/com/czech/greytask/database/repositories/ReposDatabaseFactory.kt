package com.czech.greytask.database.repositories

import com.czech.greytask.Database
import com.czech.greytask.database.Repositories_Entity
import com.czech.greytask.utils.SQLDelightConverter

class ReposDatabaseFactory(private val driverFactory: ReposDriverFactory) {

    fun createDriver(): Database {
        return Database(
            driver = driverFactory.createDriver(),
            Repositories_EntityAdapter = Repositories_Entity.Adapter(
                topicsAdapter = SQLDelightConverter.listOfStringsAdapter
            )
        )
    }
}