package com.connect.door2door.presentation

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.connect.door2door.R
import com.connect.door2door.domain.model.RiderData
import com.connect.door2door.util.Constants
import com.connect.door2door.util.Constants.EMPTY
import com.connect.door2door.util.Constants.NULL
import com.connect.door2door.util.d2dinterface.Event
import com.connect.door2door.util.extension.bitmapFromVector
import com.connect.door2door.util.extension.hide
import com.connect.door2door.util.extension.showToastLong
import com.connect.door2door.util.gmap.GoogleMapStatus
import com.connect.door2door.util.gmap.IMapStatus
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: RiderHomeViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var mapStatus: IMapStatus
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var dialog: BottomSheetDialog
    private lateinit var tvStatus: TextView
    private lateinit var tvPickUpAddress: TextView
    private lateinit var tvDropOffAddress: TextView
    private lateinit var progressBar: ProgressBar
    private val mapIcon = mutableMapOf<String, BitmapDescriptor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initViews()
        observerRiderState()
        displayToast()
    }

    override fun onStart() {
        super.onStart()
        if (this@MainActivity::mapStatus.isInitialized) connectToSocket()
    }

    override fun onStop() {
        super.onStop()
        disconnectToSocket()
    }

    private fun initViews() {
        mapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)
        progressBar = findViewById(R.id.progress_bar)
        initBottomSheet()
        initBitmap()
    }

    /**
     * This method is used for converting drawable icon to Bitmap descriptor
     */
    private fun initBitmap() {
        Constants.ICON_MAP.forEach {
            mapIcon[it.key] = bitmapFromVector(it.value)
        }
    }

    private fun initBottomSheet() {
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet, NULL)
        tvStatus = view.findViewById(R.id.tvStatus)
        tvPickUpAddress = view.findViewById(R.id.tvPickUpAddress)
        tvDropOffAddress = view.findViewById(R.id.tvDropOffAddress)
        dialog.setCancelable(false)
        dialog.setContentView(view)
    }

    private fun observerRiderState() {
        lifecycleScope.launch {
            viewModel.riderDataState.collectLatest {
                if (this@MainActivity::mapStatus.isInitialized) {
                    handleRiderEvent(it)
                }
            }
        }
    }

    private fun displayToast() {
        lifecycleScope.launch {
            viewModel.toastEvent.collectLatest { showToastLong(it) }
        }
    }

    /**
     * This method is used to handle events emitted by web socket
     * @param riderData This is the rider data which has all info about the ride
     */
    private fun handleRiderEvent(riderData: RiderData) {
        val data = riderData.data
        when (riderData.event) {
            Event.BOOKING_OPENED.event -> {
                progressBar.hide()
                updateAddress(
                    data?.pickupLocation?.address ?: EMPTY,
                    data?.dropoffLocation?.address ?: EMPTY
                )
                updateRiderState(data?.status ?: EMPTY)
                if (data != null) mapStatus.updateBookingOpenedStatus(data)
            }
            Event.VEHICLE_LOCATION_UPDATED.event -> {
                mapStatus.removeVehicleMarkers()
                mapStatus.updateVehicleStatus(data)
            }
            Event.INTERMEDIATE_STOP_LOCATION.event -> {
                mapStatus.removeInterMediateMarkers()
                if (data != null) mapStatus.updateIntermediateStatus(data)
            }
            Event.STATUS_UPDATED.event -> {
                updateRiderState(state = data?.status ?: EMPTY)
            }
            Event.BOOKING_CLOSED.event -> {
                bookingClosed()
            }
        }
    }

    /**
     * This method is used for updating rider's state at user interface
     * @param state This is the rider's state
     */
    private fun updateRiderState(state: String) {
        when (state) {
            Constants.WAITING_FOR_PICKUP -> tvStatus.text = getString(R.string.waitingForPickup)
            Constants.IN_VEHICLE -> tvStatus.text = getString(R.string.inVehicle)
            Constants.DROPPED_OF -> tvStatus.text = getString(R.string.droppedOff)
        }
    }

    /**
     * This method is used for updating rider's pickup and drop off address
     */
    private fun updateAddress(pickUpAddress: String = EMPTY, dropOffAddress: String = EMPTY) {
        if (pickUpAddress.isNotBlank()) tvPickUpAddress.text = pickUpAddress
        if (dropOffAddress.isNotBlank()) tvDropOffAddress.text = dropOffAddress
        if (!dialog.isShowing) dialog.show()
    }

    /**
     * This method is used to show booking closed status
     */
    private fun bookingClosed() {
        showToastLong(Constants.BOOKING_CLOSED)
    }

    /**
     * This method is used to make connect to socket
     */
    private fun connectToSocket() {
        viewModel.connectToSocket()
    }

    /**
     * This method is used to make disconnect to socket
     */
    private fun disconnectToSocket() {
        viewModel.disconnect()
    }

    /**
     * This method is used to get callback from google map once the object is ready
     * @param googleMap This is the google map object
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapStatus = GoogleMapStatus(mapIcon, mMap)
        connectToSocket()
    }
}