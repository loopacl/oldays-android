package cl.loopa.android.oldays

import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.ViewModel;
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.kml.KmlFeature

class OldaysMapViewModel : ViewModel() {
    // TODO: Implement the ViewModel



    fun cargaKML() : ArrayList<KmlFeature>{


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

                    return kmlDocument.mKmlRoot.mItems

                    //}
                }

            } else{
                // Toast.makeText(activity!!.applicationContext, "Error when loading KML", Toast.LENGTH_SHORT).show();

            }

        return ArrayList<KmlFeature>()
    }




}
