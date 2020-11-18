package com.andrempuerto.meli.data.source.network

import com.andrempuerto.meli.data.source.network.response.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import kotlin.collections.HashMap

interface Api {

    @GET("$SITES_URL{site_id}/search")
    suspend fun searchItemByQuery(
        @HeaderMap header: HashMap<String, String> = buildHeader(),
        @Path("site_id") siteId: String,
        @Query("q") query: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = LIMIT_RESULTS
    ): ProductsResponse

    companion object {
        const val SITES_URL = "sites/"
        const val LIMIT_RESULTS = 10

        private const val CONTENT_TYPE = "application/json"
        private const val CHANNEL = "mobile"
        private const val ACCEPT = "application/json"

        fun buildHeader(): HashMap<String, String>{
            return HashMap<String, String>().apply {
                put("Content-Type", CONTENT_TYPE)
                put("channel", CHANNEL)
                put("Accept", ACCEPT)
                put("language", Locale.getDefault().language)
            }
        }
    }
}
