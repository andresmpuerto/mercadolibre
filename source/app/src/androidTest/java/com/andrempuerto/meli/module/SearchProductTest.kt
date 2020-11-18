package com.andrempuerto.meli.module

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.andrempuerto.meli.data.ProductsPagingSource
import com.andrempuerto.meli.data.source.local.CountryPreference
import com.andrempuerto.meli.repository.CountrySiteRepository
import com.andrempuerto.meli.repository.ProductsRepository
import com.andrempuerto.meli.ui.product.ProductViewModel
import com.andrempuerto.meli.util.CoroutineTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class SearchProductTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    lateinit var repository: ProductsRepository

    @MockK
    lateinit var siteRepository: CountrySiteRepository

    lateinit var viewModel: ProductViewModel

    @Before
    fun initTest() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun finishTest() {
        unmockkAll()
    }

    @Test
    fun vTest() {
        every { siteRepository.siteIdFlow } returns flow { emit(CountryPreference(siteId = "MCO")) }
        viewModel = ProductViewModel(repository, siteRepository)
        verify { siteRepository.siteIdFlow }
        Assert.assertEquals(viewModel.siteId, "MCO")
    }

    @Test
    fun validateSearchProductByQueryTest() {
        every { siteRepository.siteIdFlow } returns flow { emit(CountryPreference(siteId = "MCO")) }
        val pagingConfig: PagingConfig = mockk()
        val pagingSource: ProductsPagingSource = mockk()

        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSource }
        )
        coEvery { repository.getSearchProducts(any(), any()) } returns pager.liveData //PagingData<Product>

        viewModel = ProductViewModel(repository, siteRepository)

        viewModel.setQuery("android")
        Assert.assertEquals(viewModel._query.value, "android")

        Assert.assertEquals(viewModel.products.value, pager)
    }
}
