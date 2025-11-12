package com.datn.apptravel.data.model.response

import com.datn.apptravel.data.model.Trip

data class TripResponse(
    val success: Boolean,
    val message: String?,
    val data: Trip?
)