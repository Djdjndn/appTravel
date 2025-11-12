package com.datn.apptravel.ui.plandetail

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datn.apptravel.databinding.ActivityActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class ActivityDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityActivityDetailBinding
    private var tripId: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityDetailBinding.inflate(layoutInflater)
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
            saveActivityDetails()
        }
        
        // Setup date pickers
        binding.etStartTime.setOnClickListener {
            showDatePicker(binding.etStartTime)
        }
        
        binding.etEndTime.setOnClickListener {
            showDatePicker(binding.etEndTime)
        }
    }
    
    private fun showDatePicker(targetEditText: android.widget.EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            targetEditText.setText(formattedDate)
        }, year, month, day).show()
    }

    private fun saveActivityDetails() {
        // Validate inputs
        if (binding.etEventName.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Add activity to trip
        tripId?.let { id ->
            val activityDetails = mapOf(
                "eventName" to binding.etEventName.text.toString(),
                "startTime" to binding.etStartTime.text.toString(),
                "endTime" to binding.etEndTime.text.toString(),
                "expense" to binding.etExpense.text.toString().ifEmpty { "0" },
                "address" to binding.etAddress.text.toString()
            )
            
            // TODO: Call ViewModel to save activity
            Toast.makeText(this, "Activity saved", Toast.LENGTH_SHORT).show()
            finish()
        } ?: run {
            Toast.makeText(this, "Trip ID is missing", Toast.LENGTH_SHORT).show()
        }
    }
}
