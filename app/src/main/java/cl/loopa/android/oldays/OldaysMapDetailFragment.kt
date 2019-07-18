package cl.loopa.android.oldays

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_oldays_map_detail.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OldaysMapDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OldaysMapDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OldaysMapDetailFragment : Fragment() {

    val args: OldaysMapDetailFragmentArgs by navArgs()

    private var listener: OnFragmentInteractionListener? = null
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oldays_map_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val name : String = args.placemark?.mName.toString()
        Log.d("kML", "Recibí: " + name)

        //Showing the title by @Rajarshi https://stackoverflow.com/q/52511136/3369131
        /*Navigation.findNavController(view)
            .getCurrentDestination()?.setLabel(name*/

        val titulo: TextView = getView()!!.findViewById(R.id.titulo) as TextView
        titulo.text = args.placemark?.mName.toString()

        val descripcion: TextView = getView()!!.findViewById(R.id.descripcion) as TextView
        descripcion.text = stripHtml(args.placemark?.mDescription.toString())

        // Google Maps KML put the list of images space separated urls in a <ExtendedData><Data name="gx_media_links">
        val urls = args.placemark?.getExtendedData("gx_media_links")!!.split(" ")
        Log.d("kML", "URLS: " + urls)

        val adapter=MapDetailSliderAdapter(context,urls)
        slider.adapter = adapter

    }


    override fun onResume() {
        super.onResume()
        activity?.toolbar?.title = args.placemark?.mName
    }

    fun stripHtml(htmls: String): Spanned {

        //Log.d("Antes", htmls)
        // Regular Expressions in Kotlin by @alessiostalla
        // https://www.baeldung.com/kotlin-regular-expressions#replacing
        var rgx = "<img.*?./>".toRegex()
        val htmlr = rgx.replace(htmls, "")
        Log.d("Después",htmlr)
        var txt : Spanned

/*
        if (htmls == null) {
            txt = SpannableString("")
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {*/

            // on API 24 and newer, otherwise flags are ignored and Html.fromHtml(String) is used
            // https://developer.android.com/reference/androidx/core/text/HtmlCompat.html
            // @Rockney https://stackoverflow.com/a/37905107/3369131
            txt = HtmlCompat.fromHtml(htmlr, HtmlCompat.FROM_HTML_MODE_LEGACY)/*

        } else {
            txt = Html.fromHtml(html)
        }*/
        return txt
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }*/
/*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }*/

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
