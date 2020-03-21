package cl.loopa.android.oldays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.galeria_item.view.*

class OldaysGaleriaViewHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(LayoutInflater.from(parent.context).inflate(R.layout.galeria_item, parent, false))

    fun bind(category: String) {

        Glide.with(itemView.context) // could be an issue!
            .load(category)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(itemView.fullscreen_content)

        //itemView.categoryName.text = category.name
    }
}