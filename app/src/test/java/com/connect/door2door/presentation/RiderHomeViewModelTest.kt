package com.connect.door2door.presentation

import app.cash.turbine.test
import com.connect.door2door.TestDispatcher
import com.connect.door2door.data.remote.repository.FakeD2DSocketServiceImplTest
import com.connect.door2door.util.Constants
import com.connect.door2door.util.d2dinterface.Event
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RiderHomeViewModelTest {
    private lateinit var riderHomeViewModel: RiderHomeViewModel
    private lateinit var testDispatchers: TestDispatcher
    private lateinit var fakeD2DSocketServiceImplTest: FakeD2DSocketServiceImplTest


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        testDispatchers = TestDispatcher()
        fakeD2DSocketServiceImplTest = FakeD2DSocketServiceImplTest()
        riderHomeViewModel = RiderHomeViewModel(testDispatchers, fakeD2DSocketServiceImplTest)
    }

    @Test
    fun `socket should return success when connected`() = runTest {
        riderHomeViewModel.connectToSocket()
        riderHomeViewModel.toastEvent.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(Constants.CONNECTION)
        }
    }

    @Test
    fun `socket should return failure when not connected`() = runTest {
        riderHomeViewModel.connectToSocket()
        riderHomeViewModel.toastEvent.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(Constants.CONNECTION_NOT_ESTABLISHED)
        }
    }

    @Test
    fun `socket should return failure when there is any exception`() = runTest {
        riderHomeViewModel.connectToSocket()
        riderHomeViewModel.toastEvent.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(Constants.UNKNOWN_ERROR)
        }
    }

    @Test
    fun `test first time, event should be bookingOpened`() = runTest {
        fakeD2DSocketServiceImplTest.setPosition(0)
        riderHomeViewModel.refreshData()
        riderHomeViewModel.riderDataState.test {
            testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
            val emission = awaitItem()
            assertThat(emission.event).isEqualTo(Event.BOOKING_OPENED.event)
        }
    }

    @Test
    fun `test when vehicle location is updated, event should be vehicleLocationUpdated`() =
        runTest {
            fakeD2DSocketServiceImplTest.setPosition(1)
            riderHomeViewModel.refreshData()
            riderHomeViewModel.riderDataState.test {
                testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
                val emission = awaitItem()
                assertThat(emission.event).isEqualTo(Event.VEHICLE_LOCATION_UPDATED.event)
            }
        }

    @Test
    fun `test when intermediate location is updated, event should be intermediateStopLocationsChanged`() =
        runTest {
            fakeD2DSocketServiceImplTest.setPosition(2)
            riderHomeViewModel.refreshData()
            riderHomeViewModel.riderDataState.test {
                testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
                val emission = awaitItem()
                assertThat(emission.event).isEqualTo(Event.INTERMEDIATE_STOP_LOCATION.event)
            }
        }

    @Test
    fun `test when rider status  is updated, event should be statusUpdated`() =
        runTest {
            fakeD2DSocketServiceImplTest.setPosition(3)
            riderHomeViewModel.refreshData()
            riderHomeViewModel.riderDataState.test {
                //testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
                val emission = awaitItem()
                assertThat(emission.event).isEqualTo(Event.STATUS_UPDATED.event)
            }
        }

    @Test
    fun `test when booking is closed, event should be bookingClosed`() =
        runTest {
            fakeD2DSocketServiceImplTest.setPosition(4)
            riderHomeViewModel.refreshData()
            riderHomeViewModel.riderDataState.test {
                testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
                val emission = awaitItem()
                assertThat(emission.event).isEqualTo(Event.BOOKING_CLOSED.event)
            }
        }

    @After
    fun tearDown() {
    }
}