package cl.loopa.android.oldays

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

/*
* Create a ImageSlider with ViewPager in Android | Android Studio 3.1.2 | Kotlin | 2018
* https://www.youtube.com/watch?v=rkABx5gh_rY
* */

class MapDetailSliderAdapter(private val context: Context?): PagerAdapter(){

    private var layoutInflater:LayoutInflater?=null
    private val images=arrayOf(R.drawable.ic_launcher_foreground)
    val urls = arrayOf(
        "https://lh6.googleusercontent.com/_NtURUEgXcvirZB6RSVvB_IIzOlUvv1rEYZKwOuDkIkQ4KXB2x4ztjK36d2zU2oOoxwif5axqKlRxW1ohMS-IPFOpuYrTG1FUNoq9WfI9TqMyOaeAsVeue2Gknmfk4jFDSaB",
        "https://lh4.googleusercontent.com/COiqaR10oAYdNBOUjHPdt_MytWSgWkqIH-_sUEIDfI09z3hzwL30LgUiaq8uGHeDxgHcP-ECLOZcIsuJYmRqerzo8tig2GvxhGeQeYyjsPX0-N_Fcr9jtULPFr0iDsW16ERYDQ")

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return urls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.map_detail_slider_item,null)


        val image = v.findViewById<View>(R.id.sliderItem) as ImageView

        Glide.with(container)
            // googleusercontent.com allows image sizing
            // https://developers.google.com/people/image-sizing
            .load(urls[position]+"=w240")
            .centerCrop()
            .into(image)

        //image.setImageResource(images[position])
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