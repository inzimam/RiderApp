package com.connect.door2door.domain.model

import com.connect.door2door.util.Constants.EMPTY
import com.connect.door2door.util.Constants.ZERO
import com.connect.door2door.util.d2dinterface.MARKER_TYPE
import kotlinx.serialization.Serializable

/**
 * Data class for storing all states of vehicle & rider
 */
@Serializable
data class Data(
    val dropoffLocation: LocationData? = LocationData(markerState = MARKER_TYPE.PICK_DROP),
    val intermediateStopLocations: List<LocationData>? = mutableListOf(),
    val pickupLocation: LocationData? = LocationData(markerState = MARKER_TYPE.PICK_DROP),
    val status: String? = EMPTY,
    val vehicleLocation: LocationData? = LocationData(markerState = MARKER_TYPE.VEHICLE),
    val address: String? = EMPTY,
    val lat: Double? = ZERO,
    val lng: Double? = ZERO,
    var markerState: MARKER_TYPE = MARKER_TYPE.PICK_DROP
)