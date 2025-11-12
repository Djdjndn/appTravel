package com.datn.apptravel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.datn.apptravel.data.model.Trip
import com.datn.apptravel.data.repository.TripRepository
import com.datn.apptravel.ui.model.ScheduleActivity
import com.datn.apptravel.ui.model.ScheduleDay
import com.datn.apptravel.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TripDetailViewModel(private val tripRepository: TripRepository) : BaseViewModel() {
    
    // Trip details
    private val _tripDetails = MutableLiveData<Trip?>()
    val tripDetails: LiveData<Trip?> = _tripDetails
    
    // Schedule days
    private val _scheduleDays = MutableLiveData<List<ScheduleDay>>()
    val scheduleDays: LiveData<List<ScheduleDay>> = _scheduleDays
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getTripDetails(tripId: String) {
        setLoading(true)
        
        viewModelScope.launch {
            try {
                if (tripId.isBlank()) {
                    _errorMessage.value = "Invalid trip ID"
                    setLoading(false)
                    return@launch
                }
                
                val result = tripRepository.getTripById(tripId)
                
                result.onSuccess { trip ->
                    _tripDetails.value = trip
                    // Generate schedule days from plans if available
                    generateScheduleDaysFromTrip(trip)
                }.onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Failed to load trip"
                    _tripDetails.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
                _tripDetails.value = null
            } finally {
                setLoading(false)
            }
        }
    }

    private fun generateScheduleDaysFromTrip(trip: Trip) {
        if (trip.plans.isNullOrEmpty()) {
            _scheduleDays.value = emptyList()
            return
        }
        
        // Group plans by date and create schedule days
        val scheduleDaysList = mutableListOf<ScheduleDay>()
        
        // TODO: Parse plans and organize into days
        // For now, just create empty schedule
        _scheduleDays.value = scheduleDaysList
    }

    fun updateTripDetails(tripId: String, updatedDetails: Map<String, Any>) {
        setLoading(true)
        
        // Simulated API call with delay
        android.os.Handler().postDelayed({
            // Simulate updating trip details
            val trip = _tripDetails.value
            _tripDetails.value = trip // Just post the same value for now
            setLoading(false)
        }, 1000)
    }
}