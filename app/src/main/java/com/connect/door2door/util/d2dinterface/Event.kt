package com.connect.door2door.util.d2dinterface

/**
 * Enum class containing all the states related to ride
 */
enum class Event(val event: String) {
    BOOKING_OPENED("bookingOpened"),
    VEHICLE_LOCATION_UPDATED("vehicleLocationUpdated"),
    INTERMEDIATE_STOP_LOCATION("intermediateStopLocationsChanged"),
    STATUS_UPDATED("statusUpdated"),
    BOOKING_CLOSED("bookingClosed")
}