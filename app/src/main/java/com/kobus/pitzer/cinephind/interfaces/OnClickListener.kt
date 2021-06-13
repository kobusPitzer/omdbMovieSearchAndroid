package com.kobus.pitzer.cinephind.interfaces

interface OnClickListener<in T> {
    fun onItemClick(clickedObject: T)
}