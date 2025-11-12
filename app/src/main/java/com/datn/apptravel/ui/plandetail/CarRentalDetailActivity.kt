package com.datn.apptravel.ui.plandetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.databinding.ActivityCarRentalDetailBinding
import java.util.Calendar

class CarRentalDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCarRentalDetailBinding
    private var tripId: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarRentalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        tripId = intent.getStringExtra("tripId")
        
        setupUI()
    }
    
    private fun setupUI() {
        // Setup back button
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        // Setup save button
        binding.btnSave.setOnClickListener {
            saveCarRentalDetails()
        }
        
        // Setup date picker
        binding.etPickupDate.setOnClickListener {
            showDatePicker()
        }
        
        // Setup time picker
        binding.etPickupTime.setOnClickListener {
            showTimePicker()
        }
    }
    
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            binding.etPickupDate.setText(formattedDate)
        }, year, month, day).show()
    }
    
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        
        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            binding.etPickupTime.setText(formattedTime)
        }, hour, minute, true).show()
    }

    private fun saveCarRentalDetails() {
        // Validate inputs
        if (binding.etRentalAgency.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add car rental to trip
        tripId?.let { id ->
            val carRentalDetails = mapOf(
                "rentalAgency" to binding.etRentalAgency.text.toString(),
                "pickupDate" to binding.etPickupDate.text.toString(),
                "pickupTime" to binding.etPickupTime.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" },
                "pickupLocation" to binding.etPickupLocation.text.toString(),
                "phone" to binding.etPhone.text.toString()
            )
            
            // TODO: Call ViewModel to save car rental
            Toast.makeText(this, "Car rental saved", Toast.LENGTH_SHORT).show()
            finish()
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}
