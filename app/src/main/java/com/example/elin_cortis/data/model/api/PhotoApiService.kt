package com.example.elin_cortis.data.model.api

import com.example.elin_cortis.data.model.PhotoModel
import retrofit2.http.GET

interface PhotoApiService {
    @GET("list")
    suspend fun getPhotos(): List<PhotoModel>
}