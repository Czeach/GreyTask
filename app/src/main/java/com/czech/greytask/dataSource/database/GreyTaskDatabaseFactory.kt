package com.czech.greytask.dataSource.database

import com.czech.greytask.Database
import com.czech.greytask.database.Repositories_Entity
import com.czech.greytask.utils.SQLDelightConverter

class GreyTaskDatabaseFactory(private val driverFactory: DriverFactory) {

    fun createDriver(): Database {
        return Database(
            driver = driverFactory.createDriver(),
            Repositories_EntityAdapter = Repositories_Entity.Adapter(
                topicsAdapter = SQLDelightConverter.listOfStringsAdapter
            )
        )
    }
}