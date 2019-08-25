package cl.loopa.android.oldays


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_oldays_galeria.*



/**
 * A simple [Fragment] subclass.
 */
class OldaysGaleriaFragment : Fragment() {

    val args: OldaysGaleriaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oldays_galeria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name : String? = args.urls?.get(args.index)
        Log.d("kML", "Recibí: " + name)
        val cual : Int = args.index
        Log.d("kML", "El: " + cual)

        //fullscreen_content.setImageResource(R.drawable.ic_launcher_foreground)

        if (args.urls!=null) {
            Glide.with(this) // could be an issue!
                .load(args.urls?.get(args.index))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(fullscreen_content)
        }
    }


    override fun onResume() {
        super.onResume()
        activity?.toolbar?.title = args.titulo
        //activity?.toolbar?.visibility = View.GONE
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
    }

}
