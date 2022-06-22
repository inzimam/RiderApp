package com.connect.door2door.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for storing intermediate status of co-riders
 */
@Serializable
data class IntermediateList(
    @SerialName("data")
    val interMediateList: List<LocationData>,
    val event: String
) {

    fun toRiderData(): RiderData {
        return RiderData(
            Data(intermediateStopLocations = interMediateList),
            event = event
        )
    }
}