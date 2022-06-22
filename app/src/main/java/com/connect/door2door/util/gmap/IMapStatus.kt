package com.connect.door2door.util.gmap

import com.connect.door2door.domain.model.Data


interface IMapStatus {
    /**
     * This method is for updating map when booking is opened
     * @param data This has all information about ride
     */
    fun updateBookingOpenedStatus(data: Data?)

    /**
     * This method is for updating intermediate position of co-riders
     * @param data This has all information about ride
     */
    fun updateIntermediateStatus(data: Data?)

    /**
     * This method is for updating vehicle position
     * @param data This has all information about ride
     */
    fun updateVehicleStatus(data: Data?)

    /**
     * This method is for removing previous co-rider's marker from map
     */
    fun removeInterMediateMarkers() {}

    /**
     * This method is for removing previous vehicle marker from map
     */
    fun removeVehicleMarkers() {}
}