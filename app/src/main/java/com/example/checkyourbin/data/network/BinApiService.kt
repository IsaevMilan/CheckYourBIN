package com.example.checkyourbin.data.network

import com.example.checkyourbin.data.BinResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path


interface BinApiService {
    @GET("{bin}")
    suspend fun getBinInfo(
        @Path("bin") bin: String
    ): Response<BinResponse>
}

