package cl.loopa.android.oldays

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.support.design.widget.BottomSheetBehavior
import com.google.maps.android.data.kml.KmlLayer




class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    //private var mBottomSheetBehavior1: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /*val bottomSheet = findViewById<View>(R.id.bottom_sheet1)
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet)*/

        //http://www.google.com/maps/d/kml?forcekml=1&mid=1X17DMfvcAVNH0qbrX-jJqSDDoAsHQYf6

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        //https://developers.google.com/maps/documentation/android-sdk/utility/kml
        val layer = KmlLayer(mMap, R.raw.oldays, applicationContext)

        layer.addLayerToMap()


    }
}
