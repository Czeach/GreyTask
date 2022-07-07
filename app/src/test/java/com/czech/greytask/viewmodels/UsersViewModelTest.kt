package com.czech.greytask.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.czech.greytask.dataSource.repositories.UsersRepository
import com.czech.greytask.dataSource.models.Users
import com.czech.greytask.ui.users.UsersViewModel
import com.czech.greytask.utils.TestRule
import com.czech.greytask.utils.states.DataState
import com.czech.greytask.utils.states.UsersState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get: Rule
    val instantTaskExecutorRule: org.junit.rules.TestRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestRule()

    @Mock
    private lateinit var usersRepository: UsersRepository

    @Mock
    private lateinit var usersViewModel: UsersViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun initMocks(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testDataFromNetwork() = testCoroutineDispatcher.runBlockingTest {

        val dataResponse = Users.Item(id = 0, login = "", avatarUrl = "", type = "")

        val query = ""

        usersViewModel = UsersViewModel(usersRepository)

        val response = DataState.data(dataResponse.toString(), listOf(dataResponse))

        val channel = Channel<DataState<List<Users.Item>>>()

        val flow = channel.consumeAsFlow()

        Mockito.`when`(usersRepository.getUsersFromNetwork(query)).thenReturn(flow)

        val job = launch {
            channel.send(response)
        }

        usersViewModel.getFromNetwork(query)

        Assert.assertEquals(true, usersViewModel.usersState.value == UsersState.Success(listOf(dataResponse)))
        Assert.assertEquals(false, usersViewModel.usersState.value == UsersState.Loading)
        Assert.assertEquals(false, usersViewModel.usersState.value == UsersState.Error(""))
        job.cancel()
    }
}