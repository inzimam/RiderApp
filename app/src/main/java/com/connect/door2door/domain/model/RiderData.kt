package com.connect.door2door.domain.model

import com.connect.door2door.util.Constants.EMPTY

/**
 * Data class for storing event and data
 */
data class RiderData(
    val `data`: Data? = Data(),
    val event: String? = EMPTY
)
