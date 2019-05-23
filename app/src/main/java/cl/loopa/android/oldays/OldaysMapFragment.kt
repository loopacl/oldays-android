package cl.loopa.android.oldays

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import android.location.Criteria
import android.location.LocationManager
import android.net.Network
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

import android.net.NetworkInfo
import android.text.Html
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.android.gms.maps.*

import org.osmdroid.bonuspack.kml.KmlFeature
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import org.osmdroid.bonuspack.kml.KmlFolder
import org.osmdroid.bonuspack.kml.KmlPlacemark

//import android.R

import cl.loopa.android.oldays.PopupPinOldaysMapAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_oldays_map.*
import kotlinx.android.synthetic.main.fragment_oldays_map.view.*


class OldaysMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: OldaysMapViewModel

    var capas = ArrayList<KmlFeature>()

    private lateinit var popup: View

    /*companion object {
        fun newInstance() = OldaysMapFragment()
    }*/



    /* onCreateView is called to inflate the layout of the fragment
      i.e graphical initialization usually takes place here.
      It is always called sometimes after the onCreate method.
       */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        /* https://www.youtube.com/watch?time_continue=95&v=x9Z0TNdAJ60
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater,R.layout.
        )*/

/*
        //  I don't remember how this code got here and what does it do
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }*/


        /* PONE EL MAPA */
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // AndroiX dropped support libraries... support
        // @ssantos https://stackoverflow.com/a/18963246/3369131
        // MapFragment requires the native API Level 11 @CommonsWare https://stackoverflow.com/a/15407738/
        // https://developers.google.com/maps/documentation/android-api/map
        val mapFragment : SupportMapFragment = SupportMapFragment.newInstance()
        //val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        //@oscarr: It would be cool to put a Loading... fragment and replace it when we have the data

        /*val mF = childFragmentManager.findFragmentByTag("map") as SupportMapFragment
        if(mF!=null && mF.isAdded){
            childFragmentManager.beginTransaction().replace(mF.id, mapFragment)/*.addToBackStack("map")*/.commitAllowingStateLoss()
        }else{*/
            childFragmentManager.beginTransaction().add(R.id.map_container, mapFragment,"map")/*.addToBackStack("map")*/.commitAllowingStateLoss()
        /*}*/
        mapFragment.getMapAsync(this)

        return inflater.inflate(R.layout.fragment_oldays_map, container, false)

    }
/*
    override fun onDestroyView() {
        try{
        val mapFragment = childFragmentManager.findFragmentByTag("map") as SupportMapFragment
        if (mapFragment != null) {
            childFragmentManager.beginTransaction().remove(mapFragment).commit()
        }}
        /*try {
            val fragment : Fragment? = (childFragmentManager.findFragmentById(R.id.map))
            val ft : FragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }*/ catch (e : Exception) {
            e.printStackTrace()
        }

        super.onDestroyView()
    }*/



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OldaysMapViewModel::class.java)


        button2.setOnClickListener{ nav_view ->
            abrirDetalle(nav_view)
        }


        // TODO: Do it in a new thread and wait for results
        // Understand Kotlin Coroutines on Android (Google I/O'19) https://www.youtube.com/watch?v=BOHK_w09pVA

        // http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
        //Thread(Runnable {
            //Aquí ejecutamos nuestras tareas costosas

            if (estaConectado()) {
                Log.d("Conectado", "SÍ")

                capas = viewModel.getKML()

            }

        //}).start()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Log.d("Mapa", "Listo")

        button2.bringToFront()

        // Add a marker in Sydney and move the camera
        val iquique = LatLng(-20.215120, -70.152103)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iquique,15f))

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
        mMap.uiSettings.isZoomControlsEnabled()
        //mMap.isMyLocationEnabled = true

        mMap.uiSettings.setMapToolbarEnabled(true)

        ponePins(capas)

        //https://pastebin.com/6UNWrrW2
        //getMap().setOnInfoWindowClickListener(this)

    }


    fun ponePins(capas : ArrayList<KmlFeature>){


        for (capa in capas) {

            Log.d("kML", "Capa: " + capa.mName)

            val lugares = capa as KmlFolder

            //if (lugares.mItems.size > 0) {
            Log.d("kML", "Tiene " + lugares.mItems.size + " lugares ")

            for (lugar: KmlFeature in lugares.mItems) {

                val punto = lugar as KmlPlacemark

                Log.d("kML", "Punto: " + punto.mName)

                val marker: Marker = mMap.addMarker(
                    MarkerOptions()
                        .position(
                            LatLng(
                                punto.mGeometry.boundingBox.centerLatitude,
                                punto.mGeometry.boundingBox.centerLongitude
                            )
                        )
                        .title(punto.mName)
                        //.snippet(stripHtml(punto.mDescription))
                )


                //TODO: Abrir detalle al hacer click en infoWindow http://www.androidcurso.com/index.php/493

            }

            }

        //}

        // Instance abstract object https://kotlinlang.org/docs/reference/object-declarations.html
        // Thanks, @Ronald Martin https://stackoverflow.com/a/34143646/3369131
        val popup = object : PopupPinOldaysMapAdapter(){
            override val inflater: LayoutInflater? = layoutInflater
        }
        mMap.setInfoWindowAdapter(popup)
    }

    // SafeArgs https://events.google.com/io/schedule/events/2d0cb491-325a-48fb-8eea-6a9452f3b33b
    // https://developer.android.com/jetpack/androidx/releases/navigation
    private fun abrirDetalle(view: View) {
        val navController = view.findNavController()
        navController.navigate(OldaysMapFragmentDirections.actionDefaultFragmentToOldaysMapDetailFragment())
    }



    /* DETECTAR SI HAY CONEXION */
    // TODO: Move it to a class
    private fun estaConectado(): Boolean {

        // from fragment Context needs getActivity()?.
        // @Zoran https://stackoverflow.com/a/24427450/3369131
        // @Trinimon https://stackoverflow.com/a/16481362/3369131
        // @Eran https://stackoverflow.com/a/25329322/

        val tipoConexion = getConnectionType(activity!!.applicationContext)

        if(tipoConexion == 0){
            //Log.d("Conectado", "NO")
            return false
        }
        //Log.d("Conectado", "SÍ")
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
/*
    fun stripHtml(html: String): String {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
           return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
           return Html.fromHtml(html).toString()
        }
    }*/

}
