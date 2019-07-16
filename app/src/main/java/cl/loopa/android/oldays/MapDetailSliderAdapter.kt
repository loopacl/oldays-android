package cl.loopa.android.oldays

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/*
* Create a ImageSlider with ViewPager in Android | Android Studio 3.1.2 | Kotlin | 2018
* https://www.youtube.com/watch?v=rkABx5gh_rY
* */

class MapDetailSliderAdapter(private val context: Context?): PagerAdapter(){

    private var layoutInflater:LayoutInflater?=null
    private val images=arrayOf(R.drawable.ic_launcher_foreground)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.map_detail_slider_item,null)
        val image = v.findViewById<View>(R.id.sliderItem) as ImageView
        image.setImageResource(images[position])
        val vp = container as ViewPager
        vp.addView(v,0)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}