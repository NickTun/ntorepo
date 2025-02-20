package com.example.nto_minipigs.Retrofit

import com.example.nto_minipigs.Retrofit.Models.Auth
import com.example.nto_minipigs.Retrofit.Models.Data
import com.example.nto_minipigs.Retrofit.Models.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

public interface ApiService {
    @GET("/api/{LOGIN}/auth")
    suspend fun auth(@Path("LOGIN") login: String): ResponseBody

    @GET("/api/info")
    suspend fun info(@Header("Authorization") token: String): Response<User>

    @PATCH("/api/{LOGIN}/open")
    suspend fun open(@Body data: Data, @Path("LOGIN") login: String): ResponseBody

    @POST("/api/login")
    suspend fun login(@Body data: Auth): Response<Data>
}