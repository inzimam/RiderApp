package com.connect.door2door.util.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * This extension method is used to convert image resource to BitmapDescriptor
 *
 * @param vectorResId This is the image resource id
 * @return BitmapDescriptor This returns BitmapDescriptor object of provided resource id
 */
fun Context.bitmapFromVector(vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
    vectorDrawable?.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )
    val bitmap = Bitmap.createBitmap(
        vectorDrawable!!.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = bitmap?.let { Canvas(it) }
    canvas?.let { vectorDrawable.draw(it) }
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun Context.showToastLong(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}