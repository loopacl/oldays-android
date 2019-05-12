package cl.loopa.android.oldays

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


abstract class PopupPinOldaysMapAdapter : GoogleMap.InfoWindowAdapter {
    private var popup: View? = null
/*
    private static final String TAG = "PopupPinOldaysMapAdapter";
    private LayoutInflater inflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.infowindow_layout, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
        ((TextView)v.findViewById(R.id.info_window_nombre)).setText("Lina Cortés");
        ((TextView)v.findViewById(R.id.info_window_placas)).setText("Placas: SRX32");
        ((TextView)v.findViewById(R.id.info_window_estado)).setText("Estado: Activo");
        return v;
    }

    @Override
    public override fun getInfoWindow(m: Marker): View? {
        return null
    }*/
    open val inflater: LayoutInflater? = null

    /*init {
        this.inflater = inflater
    }*/
/*
    internal fun PopupPinOldaysMapAdapter(inflater: LayoutInflater) {
        this.inflater = inflater
    }*/

    override fun getInfoWindow(marker: Marker?): View? {

        // https://stackoverflow.com/a/31592393/3369131
        if (marker != null) {

            popup = inflater!!.inflate(R.layout.fragment_oldays_map_popup_pin, null)
            var tv = popup!!.findViewById<View>(R.id.title) as TextView
            tv.text = marker.title
/*
            if (marker.snippet != "null") {
                tv = popup!!.findViewById<View>(R.id.snippet) as TextView
                tv.text = marker.snippet
            }*/
        }

        return null
    }


    @SuppressLint("InflateParams")
    override fun getInfoContents(marker: Marker): View? {

        //var mInfoView = context.layoutInflater.inflate(R.layout.fragment_oldays_map_popup_pin, null)

        /*if (popup == null) {

        }*/

        /*TextView tv=(TextView)popup.findViewById(R.id.title);

        tv.setText(marker.getTitle());

        //TextView n_movil= (TextView) popup.findViewById(R.id.n_movil);

        //n_movil.setText(ShopName);
*/
        return popup
    }

}
