package com.andrempuerto.meli.data

import androidx.paging.PagingSource
import com.andrempuerto.meli.data.source.network.Api
import com.andrempuerto.meli.data.source.network.Api.Companion.LIMIT_RESULTS
import com.andrempuerto.meli.data.source.network.dto.asProductModel
import com.andrempuerto.meli.model.Product
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

class ProductsPagingSource(
    private val api: Api,
    private val query: String,
    private val siteId: String
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val result = api.searchItemByQuery(
                siteId = siteId,
                query = query,
                offset = position
            )
            val repos = result.results.asProductModel()
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - LIMIT_RESULTS,
                nextKey = if (repos.isEmpty()) null else (position + LIMIT_RESULTS)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
