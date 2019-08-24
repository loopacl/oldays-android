package cl.loopa.android.oldays

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

/*
* Create a ImageSlider with ViewPager in Android | Android Studio 3.1.2 | Kotlin | 2018
* https://www.youtube.com/watch?v=rkABx5gh_rY
* */

class MapDetailSliderAdapter(
    private val context: Context?,
    private val urls:Array<String>?,
    private val titulo:String
    ): PagerAdapter(){

    private var layoutInflater:LayoutInflater?=null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        var size:Int=0
        if(urls!=null){
            size=urls.size
        }
        return size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.map_detail_slider_item,null)

        val image = v.findViewById<View>(R.id.sliderItem) as ImageView

        if(urls!=null && urls.isNotEmpty()){

            Glide.with(container)
                // googleusercontent.com allows image sizing
                // https://developers.google.com/people/image-sizing
                .load(urls[position]+"=w480")
                .placeholder(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(image)

            v.setOnClickListener {
                Log.d("foto", "Tocó: " + position)
                val action = OldaysMapDetailFragmentDirections.actionOldaysMapDetailFragmentToOldaysGaleriaFragment()
                action.index = position
                action.urls = urls
                action.titulo = titulo

                findNavController(it).navigate(action)
            }

            val vp = container as ViewPager
            vp.addView(v,0)

        }/* else{
            image.setImageDrawable()
        }*/

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}