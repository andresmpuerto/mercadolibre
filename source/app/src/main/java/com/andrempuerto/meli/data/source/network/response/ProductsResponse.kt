package com.andrempuerto.meli.data.source.network.response

import com.andrempuerto.meli.data.source.network.dto.ProductDto
import com.google.gson.annotations.SerializedName

data class ProductsResponse (
    @SerializedName("site_id") var siteId: String,
    var query: String,
    var paging: PagingResponse,
    var limit: String,
    var results: List<ProductDto>,
)

data class PagingResponse (
    var total: Int,
    var offset: Int,
    var lomit: Int,
    @SerializedName("primary_results")  var primaryResults: Int
)
