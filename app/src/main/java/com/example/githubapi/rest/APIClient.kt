package com.example.githubapi.rest

import com.example.githubapi.rest.response.GetGitRepoResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {

    @GET("search/repositories?sort=stars&order=desc&q=language:kotlin")
    suspend fun getTrendingRepo(): Response<GetGitRepoResponse>

}