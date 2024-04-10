package com.example.project2_1180320

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.project2_1180320.databinding.ActivityMainBinding
import com.example.project2_1180320.databinding.ActivitySavedLocationBinding

class SavedLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedLocationBinding
    private val database = LocationDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayAllLocation()
        binding.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayAllLocation() {
        val locationArray = database.read()
        if (locationArray.isNotEmpty()) {
            binding.displayLocations.text = ""
            for (location in locationArray) {
                binding.displayLocations.append("${location.id}\n${location.latitude} ${location.longitude}\n\n")
            }

        }
    }
}
