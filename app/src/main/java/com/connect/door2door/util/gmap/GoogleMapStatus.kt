package com.connect.door2door.util.gmap

import com.connect.door2door.domain.model.Data
import com.connect.door2door.util.Constants
import com.connect.door2door.util.Constants.EMPTY
import com.connect.door2door.util.d2dinterface.MARKER_TYPE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

/**
 * This class is used handle google map
 */
open class GoogleMapStatus @Inject constructor(
    private val mapIcon: Map<String, BitmapDescriptor>,
    private val mMap: GoogleMap
) : IMapStatus {

    private val vehicleMarkerList = mutableListOf<Marker>()
    private val interMediateMarkerList = mutableListOf<Marker>()

    override fun updateBookingOpenedStatus(data: Data?) {
        if (data?.vehicleLocation?.lat != null) {
            data.vehicleLocation.markerState = MARKER_TYPE.VEHICLE
            updateGoogleMap(data.vehicleLocation.toData(), mapIcon[Constants.VEHICLE])
        }
        if (data?.pickupLocation?.lat != null) {
            updateGoogleMap(data.pickupLocation.toData(), mapIcon[Constants.MY_LOCATION])
        }
        if (data?.dropoffLocation?.lat != null) {
            updateGoogleMap(data.dropoffLocation.toData(), mapIcon[Constants.DROP_OFF])
        }
        if (data?.intermediateStopLocations != null) {
            updateIntermediateStatus(data)
        }
    }

    override fun updateIntermediateStatus(data: Data?) {
        data?.intermediateStopLocations?.let {
            for (i in it.indices) {
                updateGoogleMap(
                    Data(
                        lat = it[i].lat,
                        lng = it[i].lng,
                        address = it[i].address,
                        markerState = MARKER_TYPE.INTER_MEDIATE
                    ),
                    mapIcon[Constants.INTER_MEDIATE]
                )
            }
        }
    }

    override fun updateVehicleStatus(data: Data?) {
        data?.markerState = MARKER_TYPE.VEHICLE
        data?.let { updateGoogleMap(it, mapIcon[Constants.VEHICLE]) }
    }

    override fun removeInterMediateMarkers() {
        for (i in interMediateMarkerList.indices) {
            interMediateMarkerList[i].remove()
        }
    }

    override fun removeVehicleMarkers() {
        for (i in vehicleMarkerList.indices) {
            vehicleMarkerList[i].remove()
        }
    }

    /*
     * This method is used to update the map once data is received
     * @param data this is all data about user's riding info
     * @param bitmapDescriptor this is the icon for showing at map
     */
    private fun updateGoogleMap(data: Data, bitmapDescriptor: BitmapDescriptor?) {
        if (data.lat != null && data.lng != null) {
            val latLng = LatLng(data.lat, data.lng)
            val marker = addMarker(latLng, data.address ?: EMPTY, bitmapDescriptor)
            addMarkerInList(marker, data.markerState)
            updateVehiclePosition(latLng, data.markerState)
        }
    }

    /**
     * This method is used to add marker at map
     * @param latLng This is the latitude & longitude of the position where marker needs to add
     * @param bitmapDescriptor icon of marker
     * @return Marker This returns marker object after adding title and icon
     */
    private fun addMarker(
        latLng: LatLng, address: String,
        bitmapDescriptor: BitmapDescriptor?
    ): Marker? {
        return mMap.addMarker(
            MarkerOptions().position(latLng).title(address)
                .icon(bitmapDescriptor)
        )
    }

    /**
     * This method is used to add marker in list so that it can be removed later if it is needed
     * @param marker This is the marker object which will be added in list
     * @param markerState This is the type of marker
     */
    private fun addMarkerInList(marker: Marker?, markerState: MARKER_TYPE) {
        marker?.let {
            when (markerState) {
                MARKER_TYPE.INTER_MEDIATE -> interMediateMarkerList.add(it)
                MARKER_TYPE.VEHICLE -> vehicleMarkerList.add(it)
                MARKER_TYPE.PICK_DROP -> return
            }
        }
    }

    /**
     * This method is used to update the vehicle position at google map
     * @param latLng This is the latitude & longitude of the vehicle position
     * @param markerState This is the type of marker
     */
    private fun updateVehiclePosition(latLng: LatLng, markerState: MARKER_TYPE) {
        if (markerState == MARKER_TYPE.VEHICLE)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constants.MAP_ZOOM))
    }
}