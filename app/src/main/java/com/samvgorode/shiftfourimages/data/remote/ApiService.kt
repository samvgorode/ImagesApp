package com.samvgorode.shiftfourimages.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos")
    suspend fun getImages(
        @Query("page") page: Int, //default: 1
        @Query("per_page") perPage: Int, //default: 10
        @Query("client_id") clientId: String,
    ): List<ImagesResponseItem>
}