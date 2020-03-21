package cl.loopa.android.oldays


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.get
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


        val adapter = OldaysGaleriaAdapter()

        pager.adapter = adapter
        adapter.setItem(args.urls)

        //pager.setCurrentItem(cual, false)

        // I just copied this @Daniel Kim code https://stackoverflow.com/a/57516428/3369131 and it worked
        val recyclerView = pager.getChildAt(0)
        recyclerView.apply {
            val itemCount = adapter?.itemCount ?: 0
            if (itemCount >= cual) {
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)

                        // False for without animation scroll
                        pager.setCurrentItem(cual, false)
                    }
                })
            }
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

    override fun onResume() {
        super.onResume()
        activity?.toolbar?.title = args.titulo
        //activity?.toolbar?.visibility = View.GONE
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
    }

}
