package com.connect.door2door.data.remote.dto

import com.connect.door2door.domain.model.Data
import com.connect.door2door.domain.model.RiderData
import kotlinx.serialization.Serializable

/**
 * This data transfer object class is used for storing the data in client server communication
 */
@Serializable
data class RiderDataDto(
    val `data`: Data,
    val event: String
) {
    fun toRiderData(): RiderData {
        return RiderData(
            data = data,
            event = event
        )
    }
}