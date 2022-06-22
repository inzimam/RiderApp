package com.connect.door2door.util.extension

import android.view.View

/**
 * This ext method is to show the component
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * This ext method is to hide the component
 */
fun View.hide() {
    this.visibility = View.GONE
}