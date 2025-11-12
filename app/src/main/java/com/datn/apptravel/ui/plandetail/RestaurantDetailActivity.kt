package com.datn.apptravel.ui.plandetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.databinding.ActivityRestaurantDetailBinding
import java.util.Calendar

class RestaurantDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRestaurantDetailBinding
    private var tripId: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
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
            saveRestaurantDetails()
        }
        
        // Setup date picker
        binding.etDate.setOnClickListener {
            showDatePicker()
        }
        
        // Setup time picker
        binding.etTime.setOnClickListener {
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
            binding.etDate.setText(formattedDate)
        }, year, month, day).show()
    }
    
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        
        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            binding.etTime.setText(formattedTime)
        }, hour, minute, true).show()
    }

    private fun saveRestaurantDetails() {
        // Validate inputs
        if (binding.etRestaurantName.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add restaurant to trip
        tripId?.let { id ->
            val restaurantDetails = mapOf(
                "restaurantName" to binding.etRestaurantName.text.toString(),
                "date" to binding.etDate.text.toString(),
                "time" to binding.etTime.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" },
                "address" to binding.etAddress.text.toString()
            )
            
            // TODO: Call ViewModel to save restaurant
            Toast.makeText(this, "Restaurant saved", Toast.LENGTH_SHORT).show()
            finish()
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}
