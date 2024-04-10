package com.example.project2_1180320

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SavedLocationActivity : AppCompatActivity() {
    val database = LocationDatabase(this)
    lateinit var displayLocations: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_location)

        val backBtn: Button = findViewById(R.id.backBtn)
        displayLocations = findViewById(R.id.displayLocations)
        displayAllLocation()
        backBtn.setOnClickListener() {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }

    private fun displayAllLocation() {
        val locationArray = database.read()
        if (locationArray.isNotEmpty()) {
            displayLocations.text = ""
            for (location in locationArray) {
                displayLocations.append("${location.id}\n${location.Latitude} ${location.Longitude}\n\n")
            }

        }
    }
}
