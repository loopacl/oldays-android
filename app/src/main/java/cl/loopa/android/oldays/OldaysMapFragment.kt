package cl.loopa.android.oldays

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.hardware.SensorManager
import android.net.Network
import android.os.StrictMode
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

import android.net.NetworkInfo
import android.widget.Toast
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.kml.KmlFeature
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import org.json.JSONObject
import org.osmdroid.bonuspack.kml.KmlFolder
import org.osmdroid.bonuspack.kml.KmlPlacemark


//import android.R




class OldaysMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    companion object {
        fun newInstance() = OldaysMapFragment()
    }

    private lateinit var viewModel: OldaysMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
/*
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Androix dropped support libraries... support
        //
        var mapFragment : SupportMapFragment = SupportMapFragment.newInstance()
        //mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        childFragmentManager.beginTransaction().add(R.id.map, mapFragment, "tag").commit()
        mapFragment.getMapAsync(this)

        return inflater.inflate(R.layout.fragment_oldays_map, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OldaysMapViewModel::class.java)
        // TODO: Use the ViewModel

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val iquique = LatLng(-20.2304908,-70.1398506)
        mMap.addMarker(MarkerOptions().position(iquique).title("Iquique glorioso"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iquique,13f))

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)

        cargaKML()
    }

    // https://github.com/googlemaps/android-maps-utils/blob/master/demo/src/com/google/maps/android/utils/demo/BaseDemoActivity.java
    private fun getMap(): GoogleMap {
        return this.mMap
    }


    fun cargaKML() {

        if (estaConectado()) {
            Log.d("Conectado", "SÍ")

            val urlString = "http://www.google.com/maps/d/kml?forcekml=1&mid=1X17DMfvcAVNH0qbrX-jJqSDDoAsHQYf6"

            val kmlDocument = KmlDocument()

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            var ok: Boolean
            try {

                ok = kmlDocument.parseKMLUrl(urlString)

            } catch (e: Exception) {
                e.printStackTrace()
                ok = false
            }

            if (ok) {
                Log.d("Descargado", "OK")

                if (kmlDocument.mKmlRoot != null) {
                    //if (kmlDocument.mKmlRoot.mItems.size > 0) {

                        for (capa in kmlDocument.mKmlRoot.mItems) {

                            Log.d("kML", "Capa: " + capa.mName)

                            val lugares = capa as KmlFolder

                            //if (lugares.mItems.size > 0) {
                                Log.d("kML", "Tiene " + lugares.mItems.size + " lugares ")

                                for (lug: KmlFeature in lugares.mItems) {

                                    val punto = lug as KmlPlacemark

                                    Log.d("kML", "Punto: "+punto.mName)

                                    val marker: Marker = getMap().addMarker(
                                        MarkerOptions()
                                            .position(LatLng(punto.mGeometry.boundingBox.centerLatitude, punto.mGeometry.boundingBox.centerLongitude))
                                            .title(punto.mName)
                                            .snippet(punto.getExtendedData("description")))

                                }

                            //}
                        }

                    //}
                }


            } else{
                Toast.makeText(activity!!.applicationContext, "Error when loading KML", Toast.LENGTH_SHORT).show();
            }


        } else {
            Log.d("Conectado", "NO")
        }

    }


    /* DETECTAR SI HAY CONEXION */
    private fun estaConectado(): Boolean {

        // from fragment Context needs getActivity()?.
        // @Zoran https://stackoverflow.com/a/24427450/3369131
        // @Trinimon https://stackoverflow.com/a/16481362/3369131
        // @Eran https://stackoverflow.com/a/25329322/

        val tipoConexion = getConnectionType(activity!!.applicationContext)

        if(tipoConexion == 0){
            Log.d("Conectado", "NO")

            return false
        }
        Log.d("Conectado", "SÍ")
        return true
    }

    // Función tipo de conexión para SDK28 (Android P) by @user1032613 https://stackoverflow.com/a/53243938/3369131
    // @IntRange(from = 0, to = 2)
    private fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = 2
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = 1
                    }
                }
            }
        }
        return result
    }

}
