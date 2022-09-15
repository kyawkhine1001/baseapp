package com.kkk.baseapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kkk.baseapp.mock.MockEMarketRepository
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.baseapp.util.TestCoroutineRule
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.mylibrary.network.ResourceState
import kotlinx.coroutines.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EMarketViewModelTest {

    private lateinit var viewModel:EMarketViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    internal val testCoroutineRule = TestCoroutineRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel = EMarketViewModel(MockEMarketRepository(),UnconfinedTestDispatcher())
    }

    @After
    fun tearDown(){
    }

    @Mock
    lateinit var storeInfoObserver: Observer<ResourceState<EMarketShopResponse>>


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetStoreInfo(){
        runTest(UnconfinedTestDispatcher()) {
            val testDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(testDispatcher)
//            viewModel.storeInfoLD.observeForever(storeInfoObserver)
            val myJob = launch {
                viewModel.getStoreInfo()
            }
            val deferred = async {
                delay(1_000)
                async {
                    delay(1_000)
                }.await()
            }

            deferred.await()
            val storeInfo = viewModel.storeInfoLD
//            Mockito.verify(storeInfoObserver).onChanged(ResourceState.Success(EMarketShopResponse("10","KKK Test","9",4.5)))
//            Assert.assertTrue(storeInfo.value != null)
            Assert.assertTrue(storeInfo.value?.successData != null)
            Assert.assertEquals(storeInfo.value?.successData?.name,"KKK Test")
            myJob.cancel()
        }
    }
}