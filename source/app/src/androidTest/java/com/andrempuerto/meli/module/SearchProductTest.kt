package com.andrempuerto.meli.module

import androidx.lifecycle.MutableLiveData
import com.andrempuerto.meli.data.source.local.CountryPreference
import com.andrempuerto.meli.repository.CountrySiteRepository
import com.andrempuerto.meli.repository.ProductsRepository
import com.andrempuerto.meli.ui.product.ProductViewModel
import com.andrempuerto.meli.util.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
//@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchProductTest {

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    lateinit var repository: ProductsRepository

    @MockK
    lateinit var siteRepository: CountrySiteRepository

    lateinit var viewModel: ProductViewModel

    @Before
    fun initTest() {
        // hiltRule.inject()
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun finishTest() {
        // unmockkAll()
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
        viewModel = ProductViewModel(repository, siteRepository)

        coEvery { repository.getSearchProducts(any(), any()) } returns MutableLiveData()

        viewModel.setQuery("MCO")

        //Assert.assertEquals(viewModel.products,  repository.getSearchProducts(any(), any()).emit() }
    }

}