package com.connect.door2door.util

import com.connect.door2door.R

object Constants {
    const val BASE_URL = "wss://d2d-frontend-code-challenge.herokuapp.com"
    const val BOOKING_CLOSED = "Your booking is completed. Hope you have enjoyed the ride."
    const val MAP_ZOOM = 14.0f
    const val MY_LOCATION = "myLocation"
    const val INTER_MEDIATE = "interMediate"
    const val VEHICLE = "vehicle"
    const val DROP_OFF = "dropOff"
    const val WAITING_FOR_PICKUP = "waitingForPickup"
    const val IN_VEHICLE = "inVehicle"
    const val DROPPED_OF = "droppedOff"
    const val EMPTY = ""
    val NULL = null
    const val ZERO = 0.0
    const val CONNECTION = "Connected"
    const val CONNECTION_NOT_ESTABLISHED = "Connection not established"
    const val UNKNOWN_ERROR = "Unknown Error"
    val ICON_MAP = mutableMapOf(
        MY_LOCATION to R.drawable.ic_my_location,
        INTER_MEDIATE to R.drawable.ic_inter_mediate,
        VEHICLE to R.drawable.ic_vehicle_location,
        DROP_OFF to R.drawable.ic_drop_off
    )
}