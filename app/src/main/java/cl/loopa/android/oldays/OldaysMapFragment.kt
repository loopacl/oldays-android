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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.hardware.SensorManager
import android.net.Network
import android.os.StrictMode
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.kml.KmlFeature
import android.net.NetworkInfo
import android.widget.Toast


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
        val mapFragment : MapFragment = MapFragment.newInstance()
        mapFragment.getMapAsync(this)
/*
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

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
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
        Log.d("Conectado", "Hola")

        cargaKML()

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
                Log.d("Conectado", "OK")

                if (kmlDocument.mKmlRoot != null) {
                    val capas = kmlDocument.mKmlRoot.mItems

                    for (capa : KmlFeature in capas) {

                        Log.d("kML", "es Folder: "+capa.mName)

                        //val folder = capa.getBoundingBox()
                        //val extendedData = capa.extendedDataAsText
                        //.hasGeometry(KmlPlacemark).toString()

                        //for (entry : KmlPlacemark in extendedData) {

                        //Log.d("kML", "es lugar "+folder.)

//                    }

                        //Log.d("Nancy", "es lugares: "+extendedData)


/*
                    for (entry : org.osmdroid.bonuspack.kml.KmlFolder in capa) {
                        /*String name = entry.getKey();
                        String value = entry.getValue();
                        result.append(name+"="+value+"<br>\n");
                    }
*/

                    for(lugar : KmlMultiGeometry in capa as KmlMultiGeometry){

            //TODO: if (lugar.hasGeometry(KmlPoint.class)) {

            //val placemark = lugar// as KmlPlacemark

            //placemark.length
            /*
                        val punto: GeoPoint = lugar.mGeometry.mCoordinates[0]

                        mMap.addMarker(MarkerOptions()
                                .position(LatLng(punto.latitude, punto.longitude))
                                .title(lugar.mName)
                                .snippet(lugar.mDescription)
                                //.icon(BitmapDescriptorFactory.fromResource(R.raw.ic_))
                        )
                }*/

            */
                    }


                }

            } else{
                Toast.makeText(activity!!.applicationContext, "Error when loading KML", Toast.LENGTH_SHORT).show();
            }


        } else {
            Log.d("Conectado", "NO")
        }

    }


    /* DETECTAR SI HAY CONEXION */
    fun estaConectado(): Boolean {

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
    fun getConnectionType(context: Context): Int {
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
