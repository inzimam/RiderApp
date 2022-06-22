package com.connect.door2door.data.remote.repository

import com.connect.door2door.data.remote.ConnectionState
import com.connect.door2door.domain.model.RiderData
import com.connect.door2door.domain.repository.D2DSocketService
import com.connect.door2door.util.Constants
import com.connect.door2door.util.extension.parseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeD2DSocketServiceImplTest : D2DSocketService {

    private var pos = 0
    private val jsonBookingOpened =
        "{\"event\":\"bookingOpened\",\"data\":{\"status\":\"waitingForPickup\",\"vehicleLocation\":{\"address\":null,\"lng\":13.426789,\"lat\":52.519061},\"pickupLocation\":{\"address\":\"Volksbühne Berlin\",\"lng\":13.411632,\"lat\":52.52663},\"dropoffLocation\":{\"address\":\"Gendarmenmarkt\",\"lng\":13.393208,\"lat\":52.513763},\"intermediateStopLocations\":[{\"address\":\"The Sixties Diner\",\"lng\":13.399356,\"lat\":52.523728},{\"address\":\"Friedrichstraße Station\",\"lng\":13.388238,\"lat\":52.519485}]}}"
    private val vehicleLocationUpdated =
        "{\"event\":\"vehicleLocationUpdated\",\"data\":{\"address\":null,\"lng\":13.38809609413147,\"lat\":52.51984751351635}}"
    private val intermediateStopUpdated =
        "{\"event\":\"intermediateStopLocationsChanged\",\"data\":[{\"lat\":52.519485,\"lng\":13.388238,\"address\":\"Friedrichstraße Station\"},{\"lat\":52.514742,\"lng\":13.389485,\"address\":\"Französische Straße Station\"}]}"
    private val riderStatusUpdated = "{\"event\":\"statusUpdated\",\"data\":\"inVehicle\"}"
    private val bookingClosed = "{\"event\":\"bookingClosed\",\"data\":null}"

    private val list = mutableListOf(
        jsonBookingOpened,
        vehicleLocationUpdated,
        intermediateStopUpdated,
        riderStatusUpdated,
        bookingClosed
    )

    fun setPosition(pos: Int) {
        this.pos = pos
    }

    override suspend fun openSession(): ConnectionState {
        return ConnectionState.Connected(Constants.CONNECTION)
    }

    override fun refreshData(): Flow<RiderData> {
        return flow { parseData(list[pos]) }
    }

    override suspend fun closeSession() {
        TODO("Not yet implemented")
    }

}