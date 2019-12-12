package com.example.composenetworking.repo

import com.example.composenetworking.data.Article
import com.example.composenetworking.data.PaperNew
import com.example.composenetworking.network.APIService

class PaperNewRepository {
    private val apiService = APIService.create()
    suspend fun getNewPaper(): PaperNew? {
        return try {
            val response = apiService.getNewPaper("us", "30f7f8ed43804cc2b135432b27c8b887")
            response.await()
        } catch (e: Exception) {
            null
        }
    }
}
