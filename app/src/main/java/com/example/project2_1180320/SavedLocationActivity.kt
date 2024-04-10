package com.example.project2_1180320

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val stringBuilder = StringBuilder()
            for (location in locationArray) {
                stringBuilder.append("${location.id}\nLatitude: ${location.latitude}\nLongitude: ${location.longitude}\n\n")
            }
            binding.displayLocations.text = stringBuilder.toString()
        } else {
            binding.displayLocations.text = getString(R.string.no_location_saved)
        }
    }
}
