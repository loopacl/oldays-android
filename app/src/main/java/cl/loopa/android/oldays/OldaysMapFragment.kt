package cl.loopa.android.oldays

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.net.Network
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

import android.net.NetworkInfo
import androidx.navigation.findNavController

import org.osmdroid.bonuspack.kml.KmlFeature
import com.google.android.gms.maps.model.Marker
import org.osmdroid.bonuspack.kml.KmlFolder
import org.osmdroid.bonuspack.kml.KmlPlacemark

//import android.R

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.MapStyleOptions


class OldaysMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: OldaysMapViewModel

    var capas = ArrayList<KmlFeature>()
    var places = ArrayList<KmlPlacemark>()

    //private lateinit var popup: View

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
        if(mF!=null && mF.isAdded){*/
            childFragmentManager.beginTransaction().replace(R.id.map_container, mapFragment)/*.addToBackStack("map")*/.commit/*AllowingStateLoss*/()
        /*}else{
            childFragmentManager.beginTransaction().add(R.id.map_container, mapFragment,"map")/*.addToBackStack("map")*/.commitAllowingStateLoss()
        }*/
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


/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // TODO: Do it in a new thread and wait for results
        // Understand Kotlin Coroutines on Android (Google I/O'19) https://www.youtube.com/watch?v=BOHK_w09pVA

        // http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
        //Thread(Runnable {
            //Aquí ejecutamos nuestras tareas costosas


        //}).start()

    }*/


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Log.d("Mapa", "Listo")

        //Quita botones
        //mMap.getUiSettings().setMapToolbarEnabled(false)

        //TODO: Verify Google Play Services version, maybe

        //Adding a styled map
        //https://developers.google.com/maps/documentation/android-sdk/styling
        //https://developers.google.com/maps/documentation/android-api/hiding-features
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success : Boolean = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context, R.raw.map_style_oldays))

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e : Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }

        //mMap.setPadding(toolbar.height, 0, 0, 0)

        //Move the camera
        val iquique = LatLng(-20.215120, -70.152103)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iquique,16f))

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
        mMap.uiSettings.setZoomControlsEnabled(true)
        //mMap.isMyLocationEnabled = true

        mMap.uiSettings.setMapToolbarEnabled(true)

        if (estaConectado()) {
            Log.d("Conectado", "SÍ")

            viewModel = ViewModelProviders.of(this).get(OldaysMapViewModel::class.java)
            capas = viewModel.getKML()

        }

        ponePins(capas)

        //https://pastebin.com/6UNWrrW2
        mMap.setOnInfoWindowClickListener(this)

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

                places.add(punto)


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


    // Abrir detalle al hacer click en infoWindow
    // https://developers.google.com/maps/documentation/android-sdk/marker#associate_data_with_a_marker
    override fun onInfoWindowClick(marker: Marker) {

        Log.d("Marcador", marker.id)
        Log.d("Largo places",places.size.toString())

        loop@ for (indice in places.indices) {

            Log.d("Marcador", marker.id.substring(1))
            Log.d("Place",indice.toString())

            //Buscar el clickeado (marker.id asignado es m<ìndice>, quitamos el 1er caracter m y comparamos
            if (indice.toString()==marker.id.substring(1)) {

                Log.d("Encontrado","YEAH")

                val action = OldaysMapFragmentDirections.actionDefaultFragmentToOldaysMapDetailFragment(places[indice])

                val navController = view?.findNavController()

                navController?.navigate(action)

                break@loop
            }
        }
    }


    // SafeArgs https://events.google.com/io/schedule/events/2d0cb491-325a-48fb-8eea-6a9452f3b33b
    // https://developer.android.com/jetpack/androidx/releases/navigation
    /*private fun abrirDetalle(view: View) {
        val navController = view.findNavController()
        navController.navigate(OldaysMapFragmentDirections.actionDefaultFragmentToOldaysMapDetailFragment())
    }*/

    /**
     *
     * @param context
     * @return true if connected to Internet
     * by @pavelnazimok https://stackoverflow.com/a/53078141/3369131
     */
    private fun estaConectado(): Boolean {
        val cm : ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < 23) {
            val ni : NetworkInfo? = cm.activeNetworkInfo

            if(ni!=null){
                return (ni.isConnected()/* && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE)*/)
            }

        } else {

            val n:Network? = cm.activeNetwork

            n?.run{
                val nc: NetworkCapabilities? = cm.getNetworkCapabilities(n)
                nc?.run{
                    if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ||
                        nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){

                        return true
                    }
                }
            }
        }

        return false
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
