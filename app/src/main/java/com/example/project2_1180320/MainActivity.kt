package com.example.project2_1180320

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.project2_1180320.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private val database = LocationDatabase(this)
    private var myMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentContainer =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment
        fragmentContainer.getMapAsync(this)

        binding.addLocationBtn.setOnClickListener { addLocation() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.satellite -> myMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.hybrid -> myMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.normal -> myMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.terrain -> myMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            R.id.viewlocations -> {
                val intent = Intent(this, SavedLocationActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onMapReady(map: GoogleMap) {
        myMap = map
        map.setOnMapClickListener { latLng ->
            map.clear()
            map.addMarker(MarkerOptions().position(latLng))
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            binding.latitudeTextView.text =
                getString(R.string.latitude_placeholder, latLng.latitude.toString())
            binding.longitudeTextView.text =
                getString(R.string.longitude_placeholder, latLng.longitude.toString())
        }
    }

    private fun addLocation() {
        val noLatitudeSelected = getString(R.string.no_latitude_selected)
        val noLongitudeSelected = getString(R.string.no_longitude_selected)

        val latitude = binding.latitudeTextView.text.toString()
        val longitude = binding.longitudeTextView.text.toString()

        if (latitude != noLatitudeSelected && longitude != noLongitudeSelected) {
            val location =
                Locations(0, latitude, longitude)
            try {
                database.insert(location)
                Toast.makeText(this, R.string.location_added, Toast.LENGTH_SHORT).show()
            } catch (err: Error) {
                Toast.makeText(this, R.string.failed_to_add_location, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, R.string.please_select_location, Toast.LENGTH_SHORT).show()
        }
    }
}
