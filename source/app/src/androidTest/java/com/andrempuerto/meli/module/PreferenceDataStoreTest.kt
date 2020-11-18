package com.andrempuerto.meli.module

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andrempuerto.meli.repository.CountrySiteRepository
import com.andrempuerto.meli.ui.home.HomeViewModel
import com.andrempuerto.meli.util.CoroutineTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class PreferenceDataStoreTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    lateinit var repository: CountrySiteRepository

    lateinit var viewModel: HomeViewModel


    @Before
    fun initTest() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun finishTest() {
        unmockkAll()
    }

    @Test
    fun validateSaveSiteIdTest() {
        viewModel = HomeViewModel(repository)
        coEvery { repository.updateSiteId(any()) } returns Unit
        viewModel.saveSelectSiteId("MCO")
        coVerify { repository.updateSiteId(any()) }

    }
}
