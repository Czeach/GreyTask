package com.czech.greytask.utils

import com.czech.greytask.Database
import com.czech.greytask.database.Repositories_Entity
import com.squareup.sqldelight.ColumnAdapter
import org.mockito.Mock

@Mock
private lateinit var dbConverter: ColumnAdapter<List<String>, String>

fun testDatabase(): Database {

    val driver = testSqlDriver()

    dbConverter = SQLDelightConverter.listOfStringsAdapter

    return Database(driver, Repositories_Entity.Adapter(
        dbConverter
    ))
}