package com.example.project2_1180320

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val database = LocationDatabase(this)
    private var myMap:GoogleMap? = null
    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView
    private lateinit var addLocationBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentContainer = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment
        fragmentContainer.getMapAsync(this)
        latitudeTextView= findViewById(R.id.latitudeTextView)
        longitudeTextView= findViewById(R.id.longitudeTextView)
        addLocationBtn= findViewById(R.id.addLocationBtn)

        addLocationBtn.setOnClickListener(){
            addLocation()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.satellite){
            myMap?.mapType= GoogleMap.MAP_TYPE_SATELLITE
        }
        if (item.itemId == R.id.hybrid){
            myMap?.mapType= GoogleMap.MAP_TYPE_HYBRID
        }
        if (item.itemId == R.id.normal){
            myMap?.mapType= GoogleMap.MAP_TYPE_NORMAL
        }
        if (item.itemId == R.id.terrain){
            myMap?.mapType= GoogleMap.MAP_TYPE_TERRAIN
        }
        if (item.itemId == R.id.viewlocations){
            val intent: Intent = Intent(this,SavedLocationActivity::class.java)
            startActivity(intent);
        }
        return true
    }

    override fun onMapReady(map: GoogleMap) {
       myMap= map
        map.setOnMapClickListener {
            map.clear()
            map.addMarker(MarkerOptions().position(it))
            map.moveCamera(CameraUpdateFactory.newLatLng(it))
            latitudeTextView.text="Latitude:" + it.latitude.toString()
            longitudeTextView.text="Longitude:"+ it.longitude.toString()

        }
    }

    fun  addLocation(){

        if (latitudeTextView.text != "NO LATITUDE SELECTED" && longitudeTextView.text != "NO LONGITUDE SELECTED") {
            val locations =
                Locations(0, latitudeTextView.text.toString(), longitudeTextView.text.toString())
            database.insert(locations)
            val st = database.writableDatabase
            Toast.makeText(this, "Location Added", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, "Please select the location", Toast.LENGTH_SHORT).show()
        }
    }
}
