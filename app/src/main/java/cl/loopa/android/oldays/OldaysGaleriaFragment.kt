package cl.loopa.android.oldays

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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

        //val name : String? = args.urls?.get(args.index)
        //Log.d("kML", "Recibí: " + name)
        val cual : Int = args.index
        //Log.d("kML", "El: " + cual)

        val adapter = OldaysGaleriaAdapter()

        pager.adapter = adapter
        adapter.setItem(args.urls)

        // Este código permite que la pantalla siguiente comience desde la foto seleccionada...
        // I just copied this @Daniel Kim code https://stackoverflow.com/a/57516428/3369131 and it worked
        val recyclerView = pager.getChildAt(0)
        recyclerView.apply {
            val itemCount = adapter.itemCount// ?: 0
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            if (itemCount >= cual) {
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
                    override fun onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)

                        // ...Principalmente esta parte
                        // False for without animation scroll
                        pager.setCurrentItem(cual, false)
                    }
                })
            }
        }


        if(args.urls!!.size <2){
            // Intento de bloquear el sweeping para poder recorrer la foto con el dedo
            pager.isUserInputEnabled = false
        }




        //adapter.list?.set(2,args.urls?.get(2))

        //fullscreen_content.setImageResource(R.drawable.ic_launcher_foreground)
/*
        if (args.urls!=null) {
            Glide.with(this) // could be an issue!
                .load(args.urls?.get(args.index))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(fullscreen_content)
        }*/
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()
        activity?.toolbar?.title = args.titulo
        //activity?.toolbar?.visibility = View.GONE
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
    }

}
