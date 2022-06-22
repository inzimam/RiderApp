package com.connect.door2door.domain.model

import com.connect.door2door.util.Constants.ZERO
import com.connect.door2door.util.d2dinterface.MARKER_TYPE
import kotlinx.serialization.Serializable

/**
 * Data class for storing actual positions of vehicle, rider & co-riders
 */
@Serializable
data class LocationData(
    val address: String? = "",
    val lat: Double? = ZERO,
    val lng: Double? = ZERO,
    var markerState: MARKER_TYPE = MARKER_TYPE.PICK_DROP
) {
    fun toData(): Data {
        return Data(
            address = address,
            lat = lat,
            lng = lng,
            markerState = markerState
        )
    }
}