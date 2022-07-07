package com.czech.greytask.dataSource.network

import com.czech.greytask.models.Repositories
import com.czech.greytask.models.UserDetails
import com.czech.greytask.models.UserRepos
import com.czech.greytask.models.Users
import com.czech.greytask.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.SEARCH_REPOS)
    suspend fun getRepos(
        @Query("q") q: String
    ): Response<Repositories>

    @GET(Constants.SEARCH_USERS)
    suspend fun getUsers(
        @Query("q") q: String
    ): Response<Users>

    @GET(Constants.GET_USER_DETAILS)
    suspend fun getUserDetails(
        @Path("username") username: String
    ): Response<UserDetails>

    @GET(Constants.GET_USER_REPOS)
    suspend fun getUserRepos(
        @Path("username") username: String
    ): Response<List<UserRepos>>
}