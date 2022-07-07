package com.czech.greytask.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.czech.greytask.dataSource.repositories.RepositoriesRepository
import com.czech.greytask.dataSource.models.Repositories
import com.czech.greytask.ui.repos.RepositoriesViewModel
import com.czech.greytask.utils.TestRule
import com.czech.greytask.utils.states.DataState
import com.czech.greytask.utils.states.ReposState
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
class ReposViewModelTest {

    @get: Rule
    val instantTaskExecutorRule: org.junit.rules.TestRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestRule()

    @Mock
    private lateinit var reposRepository: RepositoriesRepository

    @Mock
    private lateinit var repositoriesViewModel: RepositoriesViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun initMocks(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testDataFromNetwork() = testCoroutineDispatcher.runBlockingTest {

        val dataResponse = Repositories.Item(id = 0, name = "", fullName = "", description = "", stargazersCount = 0, language = "", topics = listOf("", "", ""))

        val query = ""

        repositoriesViewModel = RepositoriesViewModel(reposRepository)

        val response = DataState.data(dataResponse.toString(), listOf(dataResponse))

        val channel = Channel<DataState<List<Repositories.Item>>>()

        val flow = channel.consumeAsFlow()

        Mockito.`when`(reposRepository.getReposFromNetwork(query)).thenReturn(flow)

        val job = launch {
            channel.send(response)
        }

        repositoriesViewModel.getFromNetwork(query)

        Assert.assertEquals(true, repositoriesViewModel.reposState.value == ReposState.Success(listOf(dataResponse)))
        Assert.assertEquals(false, repositoriesViewModel.reposState.value == ReposState.Loading)
        Assert.assertEquals(false, repositoriesViewModel.reposState.value == ReposState.Error(""))
        job.cancel()
    }
}